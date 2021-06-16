package com.seek.seekblog.util;

import com.seek.seekblog.mapper.CountMapper;
import com.seek.seekblog.service.BlogService;
import com.seek.seekblog.service.impl.BlogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.TimerTask;

public class Task extends TimerTask {

    @Autowired
    private BlogService blogService;
    @Autowired
    private CountMapper countMapper;

    @Override
    public void run() {
        blogService = ApplicationContextProvider.getBean(BlogServiceImpl.class);
        countMapper =ApplicationContextProvider.getBean(CountMapper.class);
        countMapper.save(blogService.countBlog(),new Date());
    }

}
