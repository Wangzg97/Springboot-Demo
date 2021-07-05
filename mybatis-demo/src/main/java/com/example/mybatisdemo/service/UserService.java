package com.example.mybatisdemo.service;

import com.example.mybatisdemo.common.lang.PageResult;
import com.example.mybatisdemo.common.dto.PageDto;
import com.example.mybatisdemo.entity.User;

import java.util.List;

public interface UserService {
    List<User> queryAllUser();

    PageResult<List<User>> queryUserByPage(PageDto pageDto);

    void addUser(User user);

    void deleteUserByIds(List<Integer> ids);
}
