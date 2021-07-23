package com.example.securitydemo.controller;

import com.example.securitydemo.common.ReturnT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: wangzg
 * @Time: 2021/7/23
 */
@RestController
public class TestController {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/open/pwd/{pwd}")
    public Object pwd(@PathVariable("pwd") String pwd) {
        String encodePwd = bCryptPasswordEncoder.encode(pwd);

        //校验加密串和密码匹配情况
//        boolean matches = bCryptPasswordEncoder.matches(pwd, encodePwd);
//        System.out.println(matches);

        return new ReturnT(200, "生成加密密码串成功", encodePwd);
    }

    @GetMapping("/test")
    public Object test() {
        return new ReturnT(200, "测试", "");
    }

    @PreAuthorize("hasRole('admin')") //表示具有 admin角色 的用户才能够访问该接口
    @GetMapping("/test1")
    public Object test1() {
        return new ReturnT(200, "admin角色", "");
    }

    @PreAuthorize("hasAuthority('add')") //表示具有 add操作权限 的用户才能够访问该接口
    @GetMapping("/test2")
    public Object test2() {
        return new ReturnT(200, "add操作权限", "");
    }
}
