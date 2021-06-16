package com.seek.seekblog.mapper;

import com.seek.seekblog.entity.Count;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface CountMapper {

    @Insert("insert into t_count (blog_number,date) values (#{number},#{date})")
    Long save(Long number, Date date);

    @Results({
            @Result(column="blog_number",property="blogNumber"),
    })
    @Select("select * from t_count order by date desc limit 8")
    List<Count> findLestSeven();
}
