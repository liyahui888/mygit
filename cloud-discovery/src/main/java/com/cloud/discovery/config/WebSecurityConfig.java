package com.cloud.discovery.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 在/eureka/**端点忽略csrf验证
        http.csrf().ignoringAntMatchers("/eureka/**");
        // 配置使请求需要通过httpBasic或form验证
/*        http.authorizeRequests().anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic();*/
        super.configure(http);
    }
}
