package com.dabige.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dabige.enitiy.DemoData;
import com.dabige.excel.DemoDataListener;
import com.dabige.listener.MultiThreadExcelListener;
import com.dabige.mapper.ExcelMapper;
import com.dabige.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class ExcelServiceImpl extends ServiceImpl<ExcelMapper, DemoData> implements ExcelService {
    public static final String CONTENT_TYPE = "application/vnd.ms-excel";

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;
    @Autowired
    private ExcelMapper excelMapper;
    @Autowired
    private ExcelService excelService;

    @Override
    public void readExcel(MultipartFile multipartFile) {
        try {
            InputStream inputStream = multipartFile.getInputStream();
            EasyExcel.read(inputStream, DemoData.class, new DemoDataListener(excelMapper, excelService)).sheet().doRead();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 多线程插入数据库31s
     *
     * @param multipartFile
     */
    @Override
    public void readExcelAsync(MultipartFile multipartFile) {
        try {
            InputStream inputStream = multipartFile.getInputStream();
            EasyExcel.read(inputStream, DemoData.class, new MultiThreadExcelListener(excelMapper)).sheet().doRead();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 多线程导出
     *
     * @param response
     */
    @Override
    public void exportExcel(HttpServletResponse response) throws IOException, InterruptedException {
       /* ExcelWriter excelWriter = EasyExcel.write(outputStream).build();
        List<CompletableFuture> completableFutures = new ArrayList<>();
        for (int i = 0; i < sheetNum; i++) {
            int finalI = i;
            CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
                Page<DemoData> page = new Page<>();
                page.setCurrent(finalI + 1);
                page.setSize(pageSize);
                Page<DemoData> selectPage = excelMapper.selectPage(page, null);
                List<DemoData> exportList = selectPage.getRecords();
                if (!CollectionUtils.isEmpty(exportList)) {
                    WriteSheet writeSheet = EasyExcel.
                            writerSheet(finalI, "用户" + (finalI + 1)).head(DemoData.class)
                            .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).build();
                    synchronized (excelWriter) {
                        excelWriter.write(exportList, writeSheet);
                    }
                }
            }, threadPoolExecutor);
            completableFutures.add(completableFuture);
        }
        for (CompletableFuture completableFuture : completableFutures) {
            completableFuture.join();
        }
        //刷新流
        excelWriter.finish();
        outputStream.flush();
        response.getOutputStream().close();*/

        setExportHeader(response);
        ServletOutputStream outputStream = response.getOutputStream();
        Long count = excelMapper.selectCount(null);
        Integer pageSize = 000;
        Long sheetNum = count / pageSize < 1 ? 1 : (count / pageSize);
        ExecutorService executorService = Executors.newFixedThreadPool(Math.toIntExact(sheetNum));
        CountDownLatch countDownLatch = new CountDownLatch(Math.toIntExact(sheetNum));

        Map<Integer, Page<DemoData>> pageMap = new HashMap<>();
        for (int i = 0; i < sheetNum; i++) {
            int finalI = i;
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    Page<DemoData> page = new Page<>();
                    page.setCurrent(finalI + 1);
                    page.setSize(pageSize);
                    Page<DemoData> selectPage = excelMapper.selectPage(page, null);
                    pageMap.put(finalI, selectPage);
                    countDownLatch.countDown();
                }
            });
        }

        countDownLatch.await();

        try (ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(), DemoData.class).build()) {
            for (Map.Entry<Integer, Page<DemoData>> entry : pageMap.entrySet()) {
                Integer num = entry.getKey();
                Page<DemoData> salariesPage = entry.getValue();
                WriteSheet writeSheet = EasyExcel.writerSheet(num, "模板" + num).build();
                excelWriter.write(salariesPage.getRecords(), writeSheet);
            }
        }

    }


    private static void setExportHeader(HttpServletResponse response) {
        response.setContentType(CONTENT_TYPE);
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + "dabigetest.xlsx");
    }
}
