package com.example.elasticsearchdemo.utils;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Component;

@Component
public class ElasticsearchUtils {

    @Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;

    public boolean createIndex(Class<?> clazz) {
        return elasticsearchRestTemplate.indexOps(clazz).create();
    }

    public boolean existIndex(Class<?> clazz) {
        return elasticsearchRestTemplate.indexOps(clazz).exists();
    }

    public boolean existIndex(String indexName) {
        return elasticsearchRestTemplate.indexOps(IndexCoordinates.of(indexName)).exists();
    }
}
