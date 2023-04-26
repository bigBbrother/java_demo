package com.dabige.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dabige.enitiy.DemoData;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public interface ExcelService extends IService<DemoData> {
    void readExcel(MultipartFile multipartFile);

    void readExcelAsync(MultipartFile multipartFile);

    void exportExcel(HttpServletResponse response) throws IOException, InterruptedException;
}
