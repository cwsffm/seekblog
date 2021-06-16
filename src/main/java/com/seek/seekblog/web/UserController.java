package com.seek.seekblog.web;

import com.seek.seekblog.entity.User;
import com.seek.seekblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/userList")
    public List<User> listAllUser(){
        System.out.println(this.userService.listAllUsers());
        return this.userService.listAllUsers();
    }
}
