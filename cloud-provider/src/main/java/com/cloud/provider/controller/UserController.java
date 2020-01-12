package com.cloud.provider.controller;

import com.cloud.provider.entity.UserEntity;
import com.cloud.provider.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.Future;

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

    @GetMapping("/testpool")
    public void test1() throws InterruptedException {

        for (int i = 0; i <10;i++) {
            logger.info("线程：{}",Thread.currentThread().getName());
            userService.testpool(i);
        }
    }


    @GetMapping("/test2")
    public void test2() throws Exception {
        long start = System.currentTimeMillis();
        Future<String> task1 = userService.doTaskOne();
        Future<String> task2 = userService.doTaskTwo();
        Future<String> task3 = userService.doTaskThree();
        while(true) {
            if(task1.isDone() && task2.isDone() && task3.isDone()) {
                // 三个任务都调用完成，退出循环等待
                break;
            }
            Thread.sleep(1000);
        }
        long end = System.currentTimeMillis();
        System.out.println("任务全部完成，总耗时：" + (end - start) + "毫秒");
    }


    @GetMapping("/findAsync")
    public List<UserEntity> findAsync() throws Exception {
        logger.info("异步查询接口");
        List<UserEntity> user = userService.findAsync();
        ObjectMapper om = new ObjectMapper();
        String s = om.writeValueAsString(user);
        logger.info("查询结果：{}", s);
        return user;
    }


    @GetMapping("/testc")
    public List<UserEntity> testc() throws Exception {
        logger.info("testc");
        List<UserEntity> user = userService.testc();
        ObjectMapper om = new ObjectMapper();
        String s = om.writeValueAsString(user);
        logger.info("查询结果：{}", s);
        return user;
    }
}
