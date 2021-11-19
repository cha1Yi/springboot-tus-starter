package com.yidan;

import com.yidan.tus.server.TusFileUploadResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Wuxuan.Chai
 * @desc
 * @created 2021/11/19 2:45 下午
 **/
@RestController
@RequestMapping("/upload")
@SpringBootApplication
public class TusApplication {

    @Resource
    private TusFileUploadResolver tusFileUploadResolver;

    public static void main(String[] args) {
        SpringApplication.run(TusApplication.class, args);
    }


    @RequestMapping(value = {"/", "/**"}, method = {RequestMethod.POST, RequestMethod.PATCH, RequestMethod.HEAD, RequestMethod.DELETE, RequestMethod.GET})
    public void uplaod(HttpServletRequest request, HttpServletResponse response) {
        tusFileUploadResolver.upload(request, response);
    }
}
