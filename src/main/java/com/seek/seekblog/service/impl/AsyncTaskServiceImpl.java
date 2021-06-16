package com.seek.seekblog.service.impl;

import com.seek.seekblog.util.ApplicationContextProvider;
import com.seek.seekblog.util.TimerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class AsyncTaskServiceImpl {

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    @Async
    public void executeAsyncTask() {
        ApplicationContextProvider applicationContextProvider = new ApplicationContextProvider();
        applicationContextProvider.setApplicationContext(applicationContext);
        new TimerManager();
    }
}
