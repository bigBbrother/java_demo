package com.dabige.thread;

import com.dabige.enitiy.DemoData;
import com.dabige.mapper.ExcelMapper;

import java.util.List;

public class ExcelThread implements Runnable {
    private ExcelMapper excelMapper;
    private List<DemoData> list;

    public ExcelThread(ExcelMapper excelMapper, List<DemoData> list) {
        this.excelMapper = excelMapper;
        this.list = list;
    }

    @Override
    public void run() {
        excelMapper.insertBatchSomeColumn(list);
    }
}
