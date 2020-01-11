package com.cloud.client.user.feign;

import com.cloud.client.user.entity.UserEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "cloud-provider")
public interface UserFeignClient {

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public UserEntity findById(@PathVariable("id") Long id);

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public List<UserEntity> getAll() ;
}
