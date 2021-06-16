package com.seek.seekblog;

import com.seek.seekblog.service.impl.AsyncTaskServiceImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@MapperScan(basePackages = "com.seek.seekblog.mapper")
@EnableAsync
public class SeekblogApplication {

    @Autowired
    private AsyncTaskServiceImpl asyncTaskService;


    public static void main(String[] args) {
        SpringApplication.run(SeekblogApplication.class, args);
    }

}
