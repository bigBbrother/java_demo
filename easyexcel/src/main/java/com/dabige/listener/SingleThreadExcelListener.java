package com.dabige.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dabige.enitiy.DemoData;
import com.dabige.mapper.ExcelMapper;
import com.dabige.service.ExcelService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class SingleThreadExcelListener extends ServiceImpl<ExcelMapper, DemoData> implements IService<DemoData>, ReadListener<DemoData> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SingleThreadExcelListener.class);
    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 1000;
    private ThreadLocal<ArrayList<DemoData>> list = ThreadLocal.withInitial(ArrayList::new);
    private static AtomicInteger count = new AtomicInteger(1);
    /**
     * 创建线程池必要参数
     */
    private ExecutorService executorService = Executors.newFixedThreadPool(20);


    /**
     * 假设这个是一个DAO，当然有业务逻辑这个也可以是一个service。当然如果不用存储这个对象没用。
     */
    private ExcelMapper excelMapper;

    private ExcelService excelService;

    /**
     * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
     *
     * @param excelMapper
     */
    public SingleThreadExcelListener(ExcelMapper excelMapper, ExcelService excelService) {
        this.excelMapper = excelMapper;
        this.excelService = excelService;
    }

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(DemoData data, AnalysisContext context) {
        System.out.println(JSON.toJSONString(data));
        list.get().add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.get().size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            list.get().clear();
        }
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        LOGGER.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        LOGGER.info("{}条数据，开始存储数据库！", list.get().size());
        excelMapper.insertBatchSomeColumn(list.get());
        LOGGER.info("存储数据库成功！");
    }


}
