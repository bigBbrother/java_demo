package com.dabige.controller;

import com.dabige.service.ExcelService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping("/excel")
@RestController
public class ExcelController {
    @Resource
    private ExcelService excelService;


    /**
     * 百万数据导入数据库
     *
     * @param multipartFile
     */
    @PostMapping("/upload")
    public void uploadExcel(MultipartFile multipartFile) {
        //正常导入 187秒
        //excelService.readExcel(multipartFile);
        //利用多线程31秒
        excelService.readExcelAsync(multipartFile);
    }


    @GetMapping("/export")
    public void exportExcel(HttpServletResponse response) throws IOException, InterruptedException {
        excelService.exportExcel(response);
    }


}
