package com.dabige;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
@Slf4j
public class ExcelApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExcelApplication.class);
        log.info("项目启动成功...");
    }
}
