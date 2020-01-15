package com.cloud.provider.controller;

import com.cloud.provider.entity.TicketEntity;
import com.cloud.provider.entity.UserEntity;
import com.cloud.provider.entity.UserSlaveEntity;
import com.cloud.provider.mapper.UserMapper;
import com.cloud.provider.mapper.UserSlaveMapper;
import com.cloud.provider.service.ThreadTestService;
import com.cloud.provider.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@RestController
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserSlaveMapper userSlaveMapper;

    @Autowired
    private ThreadTestService  threadTestService;

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
        logger.info("开始做任务");
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
        }
        int queueSize = queue.size();
        logger.info("队列长度：{}",queueSize);
        for (int i = 0; i < queueSize; i++) {
            List<UserEntity> subAttendList = queue.take().get();
            if (!CollectionUtils.isEmpty(subAttendList)) {
                resultList.addAll(subAttendList);
            }
        }
        logger.info("结束做任务：{}", JSON.toJSONString(resultList));
        return resultList;
    }

    @GetMapping("/saveAsyncA")
    public void saveAsyncA() throws Exception {
        logger.info("开始做任务");
        //1.准备数据
        List list = userService.findAsyncC();
        //2.分批保存
        int count = list.size();
        int pageSize = 5;
        int m = count % pageSize;
        int pageCount = m == 0 ? (count / pageSize) : (count / pageSize + 1);
        logger.info("共计：{}页，共计：{}条数据",pageCount,count);
        BlockingQueue<Future<Integer>> queue = new LinkedBlockingQueue();
        if (pageCount > 0) {
            userSlaveMapper.deleteAll();
            for (int i = 1; i <= pageCount; i++) {
                Thread.sleep(0);
                List subList =null;
                if (m == 0) {
                    subList = list.subList((i - 1) * pageSize, pageSize * (i));
                } else {
                    if (i == pageCount) {
                        subList = list.subList((i - 1) * pageSize, count);
                    } else {
                        subList = list.subList((i - 1) * pageSize, pageSize * (i));
                    }
                }
                Future<Integer> future = userService.saveAsyncA(subList);
                queue.add(future);
            }
            int queueSize = queue.size();
            logger.info("队列长度：{}",queueSize);
            Integer result=0;
            for (int i = 0; i < queueSize; i++) {
                result= result+ queue.take().get();
            }
            logger.info("结束做任务：{}", JSON.toJSONString(result));
        }


    }


    @GetMapping("/findAsyncB")
    public List<UserEntity> findAsyncB() throws Exception {
        logger.info("开始做任务");
        List<UserEntity> list = userService.findAsyncB();
        logger.info("结束做任务：{}", JSON.toJSONString(list));
        return list;
    }

    @GetMapping("/findAsyncC")
    public List<UserEntity> findAsyncC() throws Exception {
        logger.info("开始做任务");
        List<UserEntity> user = userService.findAsyncC();
        logger.info("结束做任务：{}", JSON.toJSONString(user));
        return user;
    }

    private int reqNum;

    @GetMapping("/saleTicket")
    public void buyTicket() throws Exception {
        logger.info("开始做任务");
        reqNum++;
        TicketEntity ticketEntity = threadTestService.saleTicket(reqNum);
        logger.info("结束做任务：{}", JSON.toJSONString(ticketEntity));

    }



}
