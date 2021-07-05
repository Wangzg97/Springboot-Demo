package com.example.mybatisdemo.common.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 分页查询参数
 * @author wangzg
 * @date 2021/7/5 16:16
 */
public class PageDto {
    @NotNull(message = "页码不能为空")
    private Integer pageNum;
    @NotNull(message = "页面大小不能为空")
    private Integer pageSize;

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
}
