package com.cloud.provider.service.impl;

import com.cloud.provider.entity.UserEntity;
import com.cloud.provider.mapper.UserMapper;
import com.cloud.provider.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

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

    @Async
    @Override
    public Future<List<UserEntity>> getData(Integer offset, Integer pageSize) throws Exception {
        logger.info("当前线程：{}",Thread.currentThread().getId());
        Thread.sleep(3000);
        List<UserEntity> list = userMapper.findByPage(offset, pageSize);
        return new AsyncResult<>(list);
    }

    public  List findAsyncB() throws Exception{
        int count = userMapper.count();
        int pageSize = 2;
        int pageCount = (count + pageSize - 1)/pageSize;
        logger.info("共计：{}页，共计：{}条数据",pageCount,count);
        List data = new ArrayList<>();
        ExecutorService service = Executors.newFixedThreadPool(5);
        BlockingQueue<Future<List>> queue = new LinkedBlockingQueue<Future<List>>();
        for (int i = 1; i <=pageCount; i++) {
            Integer offset = (i - 1) * pageSize;
            Future<List> future = service.submit(getdt(offset, pageSize));
            ObjectMapper om = new ObjectMapper();
            String s = om.writeValueAsString(future.get());
            queue.add(future);
        }
        int queueSize = queue.size();
        for (int i = 0; i < queueSize; i++) {
            List list = queue.take().get();
            data.addAll(list);
        }
        service.shutdown();
        return  data;
    }

    private Callable<List> getdt(int offset,int pageSize) {
        Callable<List> callable = new Callable<List>() {
            public List call() throws Exception {
                Thread.sleep(3000);
                logger.info("当前线程：{}",Thread.currentThread().getId());
                List<UserEntity> list = userMapper.findByPage(offset, pageSize);
                return list;
            }
        };
        return callable;
    }

}
