package com.dabige;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class ExcelWriteTest {
    String PATH = "D:\\Demo2\\";

    @Test
    public void testWriteExcel03() throws Exception {
        //1.创建一个工作簿
        Workbook workbook = new HSSFWorkbook();
        //2.创建一个工作表
        Sheet sheet = workbook.createSheet("大逼哥统计表");
        //3.创建一个行
        Row row1 = sheet.createRow(0);
        //4.创建一个单元格(1,1)
        Cell cell11 = row1.createCell(0);
        cell11.setCellValue("今日新增观众");
        //(1,2)
        Cell cell12 = row1.createCell(1);
        cell12.setCellValue(666);

        Row row2 = sheet.createRow(1);
        //(2,1)
        Cell cell21 = row2.createCell(0);
        cell21.setCellValue("统计时间");
        //(2,2)
        Cell cell22 = row2.createCell(1);
        String time = new DateTime().toString("yyyy-MM-dd HH:mm:ss");
        cell22.setCellValue(time);


        //生成一张表(IO流) 03版本使用xls结尾
        FileOutputStream fileOutputStream = new FileOutputStream(PATH + "大逼哥统计表03.xls");
        workbook.write(fileOutputStream);
        //关闭流
        fileOutputStream.close();
        System.out.println("excel03生成完毕");
    }

    @Test
    public void testWriteExcel07() throws Exception {
        //1.创建一个工作簿
        Workbook workbook = new XSSFWorkbook();
        //2.创建一个工作表
        Sheet sheet = workbook.createSheet("大逼哥统计表");
        //3.创建一个行
        Row row1 = sheet.createRow(0);
        //4.创建一个单元格(1,1)
        Cell cell11 = row1.createCell(0);
        cell11.setCellValue("今日新增观众");
        //(1,2)
        Cell cell12 = row1.createCell(1);
        cell12.setCellValue(666);

        Row row2 = sheet.createRow(1);
        //(2,1)
        Cell cell21 = row2.createCell(0);
        cell21.setCellValue("统计时间");
        //(2,2)
        Cell cell22 = row2.createCell(1);
        String time = new DateTime().toString("yyyy-MM-dd HH:mm:ss");
        cell22.setCellValue(time);


        //生成一张表(IO流) 07版本使用xlsx结尾
        FileOutputStream fileOutputStream = new FileOutputStream(PATH + "大逼哥统计表07.xlsx");
        workbook.write(fileOutputStream);
        //关闭流
        fileOutputStream.close();
        System.out.println("excel07生成完毕");
    }

    @Test
    public void testWriteExcel03BigData() throws Exception {
        long begin = System.currentTimeMillis();
        //1.创建一个工作簿
        Workbook workbook = new HSSFWorkbook();
        //2.创建一个工作表
        Sheet sheet = workbook.createSheet();
        for (int rowNum = 0; rowNum < 60000; rowNum++) {
            Row row = sheet.createRow(rowNum);
            for (int cellNum = 0; cellNum < 10; cellNum++) {
                Cell cell = row.createCell(cellNum);
                cell.setCellValue(cellNum);
            }
        }
        System.out.println("over");
        FileOutputStream fileOutputStream = new FileOutputStream(PATH + "大数据03.xls");
        workbook.write(fileOutputStream);
        fileOutputStream.close();
        long end = System.currentTimeMillis();
        System.out.println((double) (end - begin) / 1000);
    }

    /**
     * 耗时较长
     *
     * @throws Exception
     */
    @Test
    public void testWriteExcel07BigData() throws Exception {
        long begin = System.currentTimeMillis();
        //1.创建一个工作簿
        Workbook workbook = new XSSFWorkbook();
        //2.创建一个工作表
        Sheet sheet = workbook.createSheet();
        for (int rowNum = 0; rowNum < 60000; rowNum++) {
            Row row = sheet.createRow(rowNum);
            for (int cellNum = 0; cellNum < 10; cellNum++) {
                Cell cell = row.createCell(cellNum);
                cell.setCellValue(cellNum);
            }
        }
        System.out.println("over");
        FileOutputStream fileOutputStream = new FileOutputStream(PATH + "大数据07.xlsx");
        workbook.write(fileOutputStream);
        fileOutputStream.close();
        long end = System.currentTimeMillis();
        System.out.println((double) (end - begin) / 1000);
    }

    /**
     * 速度快
     *
     * @throws Exception
     */
    @Test
    public void testWriteExcel07FastBigData() throws Exception {
        long begin = System.currentTimeMillis();
        //1.创建一个工作簿
        Workbook workbook = new SXSSFWorkbook();
        //2.创建一个工作表
        Sheet sheet = workbook.createSheet();
        for (int rowNum = 0; rowNum < 1000000; rowNum++) {
            Row row = sheet.createRow(rowNum);
            for (int cellNum = 0; cellNum < 2; cellNum++) {
                Cell cell = row.createCell(cellNum);
                cell.setCellValue(cellNum);
            }
        }
        System.out.println("over");
        FileOutputStream fileOutputStream = new FileOutputStream(PATH + "百万数据.xlsx");
        workbook.write(fileOutputStream);
        //清楚临时文件
        ((SXSSFWorkbook) workbook).dispose();
        fileOutputStream.close();
        long end = System.currentTimeMillis();
        System.out.println((double) (end - begin) / 1000);
    }
}
