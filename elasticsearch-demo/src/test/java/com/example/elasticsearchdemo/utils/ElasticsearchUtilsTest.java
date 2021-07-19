package com.example.elasticsearchdemo.utils;

import com.example.elasticsearchdemo.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ElasticsearchUtilsTest {

    @Autowired
    ElasticsearchUtils elasticsearchUtils;

    @Test
    void createIndex() {

    }

    @Test
    void existIndex() {
        System.out.println(elasticsearchUtils.existIndex(User.class));
        System.out.println(elasticsearchUtils.existIndex("user"));
    }
}