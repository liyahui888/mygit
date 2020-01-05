package com.cloud.provider.controller;

import com.cloud.provider.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/user/{id}")
    public User findById(@PathVariable Long id) {
        User user =new User();
        user.setName("hh");
        user.setAge(20);
        return user;
    }
}
