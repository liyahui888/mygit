package com.cloud.client.user.controller;

import com.cloud.client.user.entity.UserEntity;
import com.cloud.client.user.feign.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RefreshScope //开启更新功能
public class UserController {
    @Autowired
    private UserFeignClient userFeignClient;

    @Value("${server.port}")
    private String fromValue;

    @GetMapping("/api/user/{id}")
    public UserEntity findById(@PathVariable Long id) {
        UserEntity user = userFeignClient.findById(id);
        return user;
    }

    @GetMapping("/user")
    public List<UserEntity> getAll() {
        List<UserEntity> user = userFeignClient.getAll();
        return user;
    }

    /**
     * 返回配置文件中的值
     */
    @GetMapping("/from")
    @ResponseBody
    public String returnFormValue(){
        return fromValue;
    }
}
