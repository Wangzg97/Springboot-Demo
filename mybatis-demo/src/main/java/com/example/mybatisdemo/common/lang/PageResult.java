package com.example.mybatisdemo.common.lang;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果对象
 * @author wangzg
 * @date 2021/7/5 16:05
 */
public class PageResult<T extends List> implements Serializable {

    //总记录数
    private Long total = 0L;
    //结果列表
    private List list;
    //当前页码
    private int pageNum = 1;
    //页面大小
    private int pageSize = 10;
    //总页数
    private int totalPage = 1;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
