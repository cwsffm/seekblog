package com.seek.seekblog.service;

import com.seek.seekblog.entity.Count;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CountService {

    List<Count> findLastSeven();
}
