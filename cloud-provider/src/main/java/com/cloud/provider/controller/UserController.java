package com.cloud.provider.controller;

import com.cloud.provider.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/user/{id}")
    public User findById(@PathVariable Long id) {
        logger.info("this provider one");
        User user =new User();
        user.setName("this provider one");
        user.setAge(20);
        return user;
    }
}
