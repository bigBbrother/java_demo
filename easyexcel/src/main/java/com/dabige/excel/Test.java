package com.dabige.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson2.JSON;
import com.dabige.enitiy.DemoData;


import java.util.List;


public class Test implements ReadListener<DemoData> {
    private List<DemoData> cachedDataList = ListUtils.newArrayListWithExpectedSize(100);

    public void invoke(DemoData demoData, AnalysisContext analysisContext) {
        System.out.println("解析到一条数据:{}" + JSON.toJSONString(demoData));
        cachedDataList.add(demoData);
    }

    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("所有数据解析完成！" + cachedDataList.size());
    }

    public static void main(String[] args) {
        String fileName = "C:\\Users\\liaohongxin\\Desktop\\新建文件夹\\01.xlsx";
        // 这里默认每次会读取100条数据 然后返回过来 直接调用使用数据就行
        // 具体需要返回多少行可以在`PageReadListener`的构造函数设置
        EasyExcel.read(fileName, DemoData.class, new PageReadListener<DemoData>(dataList -> {
            for (DemoData demoData : dataList) {
                System.out.println("读取到一条数据{}" + JSON.toJSONString(demoData));
            }
        })).sheet().doRead();

    }
}
