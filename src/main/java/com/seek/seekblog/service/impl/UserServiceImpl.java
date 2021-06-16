package com.seek.seekblog.service.impl;

import com.seek.seekblog.entity.User;
import com.seek.seekblog.mapper.UserMapper;
import com.seek.seekblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> listAllUsers() {
        return this.userMapper.listAllUser();
    }

    @Override
    public User checkUser(String username, String password) {
        User user = userMapper.findByUsernameAndPassword(username,password);

        return user;
    }

}
