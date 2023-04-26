package com.dabige;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;

public class ExcelReadTest {
    String PATH = "D:\\Demo2\\";

    @Test
    public void testReadExcel03() throws Exception {
        //1.获取文件流
        FileInputStream fileInputStream = new FileInputStream(PATH + "Demo2大逼哥统计表03.xls");
        Workbook workbook = new HSSFWorkbook(fileInputStream);
        //2.拿去一个工作表
        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(0);
        Cell cell = row.getCell(1);
        double numericCellValue = cell.getNumericCellValue();
        System.out.println(numericCellValue);
        fileInputStream.close();

    }

    @Test
    public void testReadExcel03BigData() throws Exception {
        //1.获取文件流
        FileInputStream fileInputStream = new FileInputStream(PATH + "Demo2大逼哥统计表03.xls");
        Workbook workbook = new HSSFWorkbook(fileInputStream);
        //2.拿去一个工作表
        Sheet sheet = workbook.getSheetAt(0);
        //3.获取标题内容
        Row rowTitle = sheet.getRow(0);
        if (rowTitle != null) {
            //获取总列数用于for循环
            int cellCount = rowTitle.getPhysicalNumberOfCells();
            for (int cellNum = 0; cellNum < cellCount; cellNum++) {
                Cell cell = rowTitle.getCell(cellNum);
                if (cell != null) {
                    int cellType = cell.getCellType();
                    String cellValue = cell.getStringCellValue();
                    System.out.print(cellValue + "|");
                }
            }
            System.out.println();
        }
        //4.获取除去表头表中的数据
        int rowCount = sheet.getPhysicalNumberOfRows();
        for (int rowNum = 1; rowNum < rowCount; rowNum++) {
            Row rowData = sheet.getRow(rowNum);
            if (rowData != null) {
                //读取列
                int cellCount = rowTitle.getPhysicalNumberOfCells();
                for (int cellNum = 0; cellNum < cellCount; cellNum++) {
                    System.out.print("[" + (rowNum + 1) + "-" + (cellNum + 1) + "]");
                    Cell cell = rowData.getCell(cellNum);
                    //因为不知道列的数据类型所以需要匹配
                    if (cell != null) {
                        int cellType = cell.getCellType();
                        String cellValue = "";
                        switch (cellType) {
                            case HSSFCell.CELL_TYPE_STRING: //字符串
                                System.out.println("【String】");
                                cellValue = cell.getStringCellValue();
                                break;
                            case HSSFCell.CELL_TYPE_BOOLEAN: //布尔
                                System.out.println("【bolean】");
                                cellValue = String.valueOf(cell.getBooleanCellValue());
                                break;
                            case HSSFCell.CELL_TYPE_NUMERIC: //数字（日期或者普遍数字）
                                System.out.println("【NUMBER】");
                                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                    System.out.println("【日期】");
                                    Date date = cell.getDateCellValue();
                                    cellValue = new DateTime(date).toString("yyyy-MM-dd HH:mm:ss");

                                } else {
                                    //不是日期防止数字过长
                                    System.out.println("【数字】");
                                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                                    cellValue = cell.toString();
                                }
                                break;

                            case HSSFCell.CELL_TYPE_BLANK: //空
                                System.out.println("【BLANK】");
                                break;

                            case HSSFCell.CELL_TYPE_ERROR: //错误
                                System.out.println("【数据类型错误】");
                                break;
                        }
                        System.out.println(cellValue);
                    }
                }
            }
        }

        fileInputStream.close();

    }
}
