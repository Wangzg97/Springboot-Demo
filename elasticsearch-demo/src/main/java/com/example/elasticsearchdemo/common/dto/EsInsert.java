package com.example.elasticsearchdemo.common.dto;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.List;

public class EsInsert implements Serializable {
    private static final long serialVersionUID = 1L;
    //索引名
    private String indexName;
    //插入的数据列表
    private List<JSONObject> jsonList;

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public List<JSONObject> getJsonList() {
        return jsonList;
    }

    public void setJsonList(List<JSONObject> jsonList) {
        this.jsonList = jsonList;
    }
}
