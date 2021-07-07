package com.example.securitydemo.controller;

import cn.hutool.core.map.MapUtil;
import com.example.securitydemo.common.lang.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/test/pass")
    public ResponseResult passEncode() {
        //密码加密
        String encode = bCryptPasswordEncoder.encode("111111");
        //密码验证
        boolean matches = bCryptPasswordEncoder.matches("111111", encode);

        return ResponseResult.succ(
                MapUtil.builder()
                .put("encode", encode)
                .put("matches", matches)
                .build()
        );
    }
}
