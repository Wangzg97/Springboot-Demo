package com.example.elasticsearchdemo.controller;

import com.example.elasticsearchdemo.entity.User;
import com.example.elasticsearchdemo.utils.ElasticsearchUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EsController {
    @Autowired
    ElasticsearchUtils elasticsearchUtils;

    @GetMapping("/test")
    public String test1() {

        boolean b = elasticsearchUtils.existIndex(User.class);
        System.out.println(b);

        return "";
    }
}
