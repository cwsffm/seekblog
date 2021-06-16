package com.seek.seekblog.service.impl;

import com.seek.seekblog.entity.Count;
import com.seek.seekblog.mapper.CountMapper;
import com.seek.seekblog.service.CountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountServiceImpl implements CountService {

    @Autowired
    private CountMapper countMapper;


    @Override
    public List<Count> findLastSeven() {
        return countMapper.findLestSeven();
    }
}
