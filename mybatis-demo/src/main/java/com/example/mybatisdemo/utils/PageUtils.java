package com.example.mybatisdemo.utils;

import com.example.mybatisdemo.common.lang.PageResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 分页结果处理工具类
 * @author wangzg
 * @date 2021/7/5 16:09
 */
public class PageUtils {

    public static <T extends List> PageResult<T> build(T list) {
        PageResult<T> pageResult = new PageResult<>();
        if (list == null) {
            return pageResult;
        }
        if (list instanceof Page){
            Page<T> page = (Page<T>) list;
            pageResult.setTotal(page.getTotal());
            pageResult.setList(list);
            pageResult.setPageNum(page.getPageNum());
            pageResult.setPageSize(page.getPageSize());
            pageResult.setTotalPage(page.getPages());
        }
        return pageResult;
    }

    public static PageResult getPageResult(PageInfo<?> pageInfo) {
        PageResult pageResult = new PageResult();
        pageResult.setPageNum(pageInfo.getPageNum());
        pageResult.setPageSize(pageInfo.getPageSize());
        pageResult.setTotal(pageInfo.getTotal());
        pageResult.setTotalPage(pageInfo.getPages());
        pageResult.setList(pageInfo.getList());
        return pageResult;
    }
}
