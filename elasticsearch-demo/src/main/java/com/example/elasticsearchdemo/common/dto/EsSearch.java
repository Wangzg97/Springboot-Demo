package com.example.elasticsearchdemo.common.dto;

import java.io.Serializable;
import java.util.Map;

public class EsSearch implements Serializable {
    private static final long serialVersionUID = 1L;
    //索引名
    private String indexName;
    //开始页码
    private Integer pageNum;
    //每页数据量
    private Integer pageSize;
    //查询高亮字段名
    private String highName;
    //精确查询参数map  参数and连接
    private Map<String, Object> andMap;
    //精确查询参数map  参数or连接
    private Map<String, Object> orMap;
    //模糊查询参数map 参数and连接
    private Map<String, Object> dimAndMap;
    //模糊查询参数map 参数or连接
    private Map<String, Object> dimOrMap;

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getHighName() {
        return highName;
    }

    public void setHighName(String highName) {
        this.highName = highName;
    }

    public Map<String, Object> getAndMap() {
        return andMap;
    }

    public void setAndMap(Map<String, Object> andMap) {
        this.andMap = andMap;
    }

    public Map<String, Object> getOrMap() {
        return orMap;
    }

    public void setOrMap(Map<String, Object> orMap) {
        this.orMap = orMap;
    }

    public Map<String, Object> getDimAndMap() {
        return dimAndMap;
    }

    public void setDimAndMap(Map<String, Object> dimAndMap) {
        this.dimAndMap = dimAndMap;
    }

    public Map<String, Object> getDimOrMap() {
        return dimOrMap;
    }

    public void setDimOrMap(Map<String, Object> dimOrMap) {
        this.dimOrMap = dimOrMap;
    }
}
