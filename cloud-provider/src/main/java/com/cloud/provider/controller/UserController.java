package com.cloud.provider.controller;

import com.cloud.provider.entity.UserEntity;
import com.cloud.provider.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/user/{id}")
    public UserEntity findById(@PathVariable Long id) {
        logger.info("this provider one");
        UserEntity user = userService.get(id);
        return user;
    }


    @GetMapping("/user")
    public List<UserEntity> getAll() {
        logger.info("this provider one");
        List<UserEntity> user = userService.find();
        return user;
    }
}
