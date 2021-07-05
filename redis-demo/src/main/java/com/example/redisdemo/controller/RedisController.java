package com.example.redisdemo.controller;

import com.example.redisdemo.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController {

    @Autowired
    RedisUtils redisUtils;

    @GetMapping("/insert")
    public String test() {
        redisUtils.set("key", "value");
        return (String) redisUtils.get("key");
    }
}
