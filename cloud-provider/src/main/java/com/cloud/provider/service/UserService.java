package com.cloud.provider.service;

import com.cloud.provider.entity.UserEntity;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.List;
import java.util.concurrent.Future;

public interface UserService {

    List<UserEntity> find();

    UserEntity get(Long id);


    void  testpool(int i) throws InterruptedException;

    ListenableFuture<String> testpoolb(String name);

    Future<String> doTaskOne() throws Exception;

    Future<String> doTaskTwo() throws Exception;

    Future<String> doTaskThree() throws Exception;


    List<UserEntity> findAsync( ) throws Exception;

    List testc() throws Exception;
}
