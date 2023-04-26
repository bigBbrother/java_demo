package com.dabige.excel;

import com.alibaba.excel.EasyExcel;
import com.dabige.enitiy.DemoData;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WriteTest {
    String PATH = "D:\\Demo2\\";

    private List<DemoData> data() {
        List<DemoData> list = new ArrayList<DemoData>();
        for (int i = 0; i < 100000; i++) {
            DemoData data = new DemoData();
            data.setStr("字符串" + i);
            data.setDate(new Date());
            data.setNums(0.56);
            list.add(data);
        }
        return list;
    }

    /**
     * 最简单的写
     * <p>1. 创建excel对应的实体对象 参照{@link DemoData}
     * <p>2. 直接写即可
     */
    @Test
    public void simpleWrite() {
        // 写法1
        String fileName = PATH + "aaa.xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName, DemoData.class).sheet("模板").doWrite(data());

    }


}
