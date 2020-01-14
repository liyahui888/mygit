package com.cloud.provider.controller;

import com.cloud.provider.entity.UserEntity;
import com.cloud.provider.mapper.UserMapper;
import com.cloud.provider.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

@RestController
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

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

    @GetMapping("/testFuture")
    public void Future() throws Exception {
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

    @GetMapping("/findAsyncA")
    public List<UserEntity> findAsyncA() throws Exception {
        int count = userMapper.count();
        int pageSize = 2;
        int pageCount = (count + pageSize - 1)/pageSize;
        logger.info("共计：{}页，共计：{}条数据",pageCount,count);
        List<UserEntity> resultList = new ArrayList<>();
        BlockingQueue<Future<List<UserEntity>>> queue = new LinkedBlockingQueue();
        for (int i = 1; i <= pageCount; i++) {
            Thread.sleep(0);
            Integer offset = (i - 1) * pageSize;
            Future<List<UserEntity>> future = userService.getData(offset, pageSize);
            queue.add(future);
            ObjectMapper om = new ObjectMapper();
            String s = om.writeValueAsString(future.get());
        }
        int queueSize = queue.size();
        logger.info("队列长度：{}",queueSize);
        for (int i = 0; i < queueSize; i++) {
            List<UserEntity> subAttendList = queue.take().get();
            if (!CollectionUtils.isEmpty(subAttendList)) {
                resultList.addAll(subAttendList);
            }
        }

        logger.info("查询结果：{}", JSON.toJSONString(resultList));
        return resultList;
    }

    @GetMapping("/findAsyncB")
    public List<UserEntity> findAsyncB() throws Exception {
        logger.info("异步查询接口");
        List<UserEntity> user = userService.findAsyncB();

        logger.info("查询结果：{}", JSON.toJSONString(user));
        return user;
    }
}
