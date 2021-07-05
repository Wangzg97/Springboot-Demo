package com.example.mybatisdemo.mapper;

import com.example.mybatisdemo.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    List<User> queryAllUser();

    void addUser(User user);

    void deleteUserByIds(List<Integer> ids);
}
