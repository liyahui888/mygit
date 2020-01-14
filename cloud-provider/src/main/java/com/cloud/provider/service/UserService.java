package com.cloud.provider.service;

import com.cloud.provider.entity.UserEntity;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.List;
import java.util.concurrent.Future;

public interface UserService {

    List<UserEntity> find();
    List findAsyncB() throws Exception;
    UserEntity get(Long id);

    Future<String> doTaskOne() throws Exception;

    Future<String> doTaskTwo() throws Exception;

    Future<String> doTaskThree() throws Exception;

    Future<List<UserEntity>> getData(Integer offset, Integer pageSize) throws Exception;
}
