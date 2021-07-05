package com.example.mybatisdemo.service.impl;

import com.example.mybatisdemo.common.lang.PageResult;
import com.example.mybatisdemo.common.dto.PageDto;
import com.example.mybatisdemo.entity.User;
import com.example.mybatisdemo.mapper.UserMapper;
import com.example.mybatisdemo.service.UserService;
import com.example.mybatisdemo.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> queryAllUser() {
        return userMapper.queryAllUser();
    }

    @Override
    public PageResult<List<User>> queryUserByPage(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPageNum(), pageDto.getPageSize());
        List<User> userList = userMapper.queryAllUser();
        PageResult<List<User>> pageResult = PageUtils.build(userList);
//        PageInfo<User> pageInfo = new PageInfo<>(userList);
        return pageResult;
    }

    @Override
    public void addUser(User user) {
        userMapper.addUser(user);
    }

    @Override
    public void deleteUserByIds(List<Integer> ids) {
        userMapper.deleteUserByIds(ids);
    }
}
