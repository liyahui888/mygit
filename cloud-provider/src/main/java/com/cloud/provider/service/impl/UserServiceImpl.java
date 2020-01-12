package com.cloud.provider.service.impl;

import com.cloud.provider.entity.UserEntity;
import com.cloud.provider.mapper.UserMapper;
import com.cloud.provider.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public List<UserEntity> find() {
        return userMapper.getAll();
    }

    @Override
    public UserEntity get(Long id) {
        return userMapper.getOne(id);
    }

    @Async
    @Override
    public void testpool(int i) throws InterruptedException {
        System.out.println("线程" + Thread.currentThread().getName() + " 执行异步任务：" + i);
    }

    @Override
    public ListenableFuture<String> testpoolb(String name) {
        String res = name + ":Hello World!";
        return new AsyncResult<>(res);
    }

    private  Random random = new Random();

    @Async
    public Future<String> doTaskOne() throws Exception {
        System.out.println("开始做任务一");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        System.out.println("完成任务一，耗时：" + (end - start) + "毫秒");
        return new AsyncResult<>("任务一完成");
    }
    @Async
    public Future<String> doTaskTwo() throws Exception {
        System.out.println("开始做任务二");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        System.out.println("完成任务二，耗时：" + (end - start) + "毫秒");
        return new AsyncResult<>("任务二完成");
    }
    @Async
    public Future<String> doTaskThree() throws Exception {
        System.out.println("开始做任务三");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        System.out.println("完成任务三，耗时：" + (end - start) + "毫秒");
        return new AsyncResult<>("任务三完成");
    }

    @Override
    public List<UserEntity> findAsync() throws Exception {
        int count = userMapper.count();
        int pageSize = 10;
        int pageCount = (count + pageSize - 1)/pageSize;
        logger.info("共计：{}页，共计：{}条数据",pageCount,count);
        List<UserEntity> resultList = new ArrayList<>();
        BlockingQueue<Future<List<UserEntity>>> queue = new LinkedBlockingQueue();
        for (int i = 1; i <= pageCount; i++) {
            Thread.sleep(0);
            Integer offset = (i - 1) * pageSize;
            Future<List<UserEntity>> future = getData(offset, pageSize);
            queue.add(future);
            logger.info("当前线程：{}，查询第：{}页",Thread.currentThread().getName(),i);
        }
        int queueSize = queue.size();
        logger.info("队列长度：{}",queueSize);
        for (int i = 0; i < queueSize; i++) {
            List<UserEntity> subAttendList = queue.take().get();
            if (!CollectionUtils.isEmpty(subAttendList)) {
                resultList.addAll(subAttendList);
            }
        }
        return resultList;
    }

    @Async
    public Future<List<UserEntity>> getData(Integer offset, Integer pageSize) throws Exception {
        List<UserEntity> list = userMapper.findByPage(offset, pageSize);
        return new AsyncResult<>(list);
    }




}
