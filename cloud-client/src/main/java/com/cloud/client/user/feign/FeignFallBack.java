package com.cloud.client.user.feign;

import com.cloud.client.user.entity.UserEntity;

import java.util.List;

public class FeignFallBack implements UserFeignClient {
    @Override
    public UserEntity findById(Long id) {
        return null;
    }

    @Override
    public List<UserEntity> getAll() {
        return null;
    }
}
