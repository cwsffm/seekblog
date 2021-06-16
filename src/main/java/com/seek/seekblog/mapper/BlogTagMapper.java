package com.seek.seekblog.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BlogTagMapper {

    @Select("select tag_id from t_blog_tag where blog_id=#{id}")
    List<Long> findByBlogId(Long id);

    @Select("select * from t_blog_tag where tag_id=#{id}")
    List<Long> findByTagId(Long id);

    @Insert("insert into t_blog_tag (blog_id,tag_id)values(#{blog_id},#{tag_id})")
    Long save(Long blog_id,Long tag_id);

    @Delete("delete from t_blog_tag where id=#{id}")
    Long deleteById(Long id);

    @Delete("delete from t_blog_tag where blog_id=#{id}")
    Long deleteByBlogId(Long id);

    @Delete("delete from t_blog_tag where tag_id=#{id}")
    Long deleteByTagId(Long id);

    @Select("select count(*) from t_blog_tag where tag_id=#{id}")
    Long countByTagId(Long id);

}
