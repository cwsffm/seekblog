package com.seek.seekblog;

import com.seek.seekblog.mapper.BlogMapper;
import com.seek.seekblog.vo.BlogQuery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SeekblogApplicationTests {

    @Autowired
    private BlogMapper blogMapper;

    @Test
    void contextLoads() {
    }

}
