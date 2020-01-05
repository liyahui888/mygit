package com.cloud.client.user.feign;

import com.cloud.client.user.entity.User;

public class FeignFallBack implements UserFeignClient {
    @Override
    public User findById(Long id) {
        return null;
    }
}
