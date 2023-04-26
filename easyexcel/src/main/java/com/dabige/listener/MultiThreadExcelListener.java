package com.dabige.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dabige.enitiy.DemoData;
import com.dabige.mapper.ExcelMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
// 有个很重要的点 MultiThreadExcelListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
public class MultiThreadExcelListener extends ServiceImpl<ExcelMapper, DemoData> implements IService<DemoData>, ReadListener<DemoData> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MultiThreadExcelListener.class);
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


    /**
     * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
     *
     * @param excelMapper
     */
    public MultiThreadExcelListener(ExcelMapper excelMapper) {
        this.excelMapper = excelMapper;

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
            asyncSaveData();
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
        LOGGER.info("所有数据解析完成！");
    }

    public void asyncSaveData() {
        if (!list.get().isEmpty()) {
            ArrayList<DemoData> demoData = (ArrayList<DemoData>) list.get().clone();
            executorService.execute(new SaveTask(demoData, excelMapper));
            // 存储完成清理 list
            list.get().clear();
        }
    }

    static class SaveTask implements Runnable {

        private final List<DemoData> demoDataListFilter;
        private final ExcelMapper excelMapper;

        public SaveTask(List<DemoData> demoDataList, ExcelMapper excelMapper) {
            this.demoDataListFilter = demoDataList;
            this.excelMapper = excelMapper;

        }

        @Override
        public void run() {
            if (demoDataListFilter.size() > 0) {
                int i = excelMapper.insertBatchSomeColumn(demoDataListFilter);
            }
            log.info("第" + count.getAndAdd(1) + "次插入" + demoDataListFilter.size() + "条数据");
        }
    }
}
