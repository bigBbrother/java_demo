package com.dabige.httpclient.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping("/test")
@RestController
@Slf4j
@CrossOrigin
public class TestController {


    @GetMapping("forward")
    public void forwardTest(HttpServletRequest request, HttpServletResponse response, @RequestParam() String id, @RequestParam String ids) throws ServletException, IOException {
        String cookie = request.getHeader("Cookie");
        log.warn(cookie);
        String queryString = request.getQueryString();
        log.warn(queryString);
        log.info("转发请求进来了");
        request.getRequestDispatcher("/test/result").forward(request, response);

    }

    @GetMapping("/result")
    public void resultUrl(HttpServletRequest request, HttpServletResponse response, @RequestParam() String id, @RequestParam String ids) {
        log.info("forward转发成功");
    }

    @GetMapping("redirect")
    public void redirect(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("重定向成功");
        response.sendRedirect("https://www.baidu.com/");
    }


}
