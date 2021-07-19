package com.example.elasticsearchdemo.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RestHighLevelUtilsTest {

    @Autowired
    RestHighLevelUtils restHighLevelUtils;

    @Test
    void createIndex() {
        boolean user = restHighLevelUtils.createIndex("user");
        System.out.println(user);
    }

    @Test
    void existIndex() {
        System.out.println(restHighLevelUtils.existIndex("user"));
    }

    @Test
    void deleteIndex() {
        restHighLevelUtils.deleteIndex("user");
    }
}