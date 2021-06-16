package com.seek.seekblog.mapper;

import com.seek.seekblog.entity.Type;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TypeMapper {

    @Select("select * from t_type")
    List<Type> listAllTypes();

    @Insert("insert into t_type (name,number) values  (#{name},0)")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    Long save(Type type);

    @Update("update t_type set name=#{name} where id = #{id}")
    void update(Type type);

    @Select("select * from t_type where id=#{id}")
    Type findById(Long id);

    @Select("select * from t_type where name=#{name}")
    Type findByName(String name);

    @Delete("delete from t_type where id=#{id}")
    void delete(Long id);

    @Update("update t_type set number=number+1 where id=#{id}")
    void addTypeNumber(Long id);

    @Update("update t_type set number=number-1 where id=#{id}")
    void reduceTypeNumber(Long id);

    @Select("select count(*) from t_type")
    Long count();
}
