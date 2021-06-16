package com.seek.seekblog.service;

import com.seek.seekblog.entity.User;

import java.util.List;


public interface UserService {

    List<User> listAllUsers();

    User checkUser(String username,String password);

}
