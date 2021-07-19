package com.example.elasticsearchdemo.common.dto;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

public class EsUpdate implements Serializable {

    private static final long serialVersionUID = 1L;
    //索引名
    private String indexName;
    //文档ID
    private String documentId;
    //要更新的数据
    private JSONObject json;

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public JSONObject getJson() {
        return json;
    }

    public void setJson(JSONObject json) {
        this.json = json;
    }
}
