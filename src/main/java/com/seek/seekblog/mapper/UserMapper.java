package com.seek.seekblog.mapper;


import com.seek.seekblog.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    @Results({
            @Result(column="create_time",property="createTime"),
            @Result(column="update_time",property="updateTime")
    })
    @Select("select * from t_user")
    List<User> listAllUser();

    @Select("select * from t_user where id=#{id}")
    User findById(Long id);

    @Select("select * from t_user where username=#{username} and password=#{password}")
    User findByUsernameAndPassword(String username,String password);

}
