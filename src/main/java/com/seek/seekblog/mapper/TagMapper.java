package com.seek.seekblog.mapper;

import com.seek.seekblog.entity.Tag;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TagMapper {

    @Select("select * from t_tag")
    List<Tag> listAllTags();

    @Insert("insert into t_tag (name,number) values  (#{name},0)")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    Long save(Tag tag);

    @Update("update t_tag set name=#{name} where id = #{id}")
    void update(Tag tag);

    @Select("select * from t_tag where id=#{id}")
    Tag findById(Long id);

    @Select("select * from t_tag where name=#{name}")
    Tag findByName(String name);

    @Delete("delete from t_tag where id=#{id}")
    void delete(Long id);

    @Update("update t_tag set number=number+1 where id = #{id}")
    void addTagNumber(Long id);

    @Update("update t_tag set number=number-1 where id = #{id}")
    void reduceTagNumber(Long id);

    @Select("select count(*) from t_tag")
    Long count();

}
