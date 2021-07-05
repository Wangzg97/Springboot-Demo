package com.example.mybatisdemo.controller;

import com.example.mybatisdemo.common.lang.PageResult;
import com.example.mybatisdemo.common.lang.ResponseResult;
import com.example.mybatisdemo.common.dto.PageDto;
import com.example.mybatisdemo.entity.User;
import com.example.mybatisdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/queryAllUser")
    public ResponseResult queryAllUser() {
        List<User> userList = userService.queryAllUser();
        return ResponseResult.succ(userList);
    }

    @PostMapping("/queryByPage")
    public ResponseResult queryByPage(@Validated @RequestBody PageDto pageDto) {
        PageResult<List<User>> pageResult = userService.queryUserByPage(pageDto);
        return ResponseResult.succ(pageResult);
    }

    @PostMapping("/addUser")
    public ResponseResult addUser(@RequestBody User user) {
        userService.addUser(user);
        return ResponseResult.succ("");
    }

    @DeleteMapping("/deleteUserById")
    public ResponseResult addUser(@RequestBody List<Integer> ids) {
        userService.deleteUserByIds(ids);
        return ResponseResult.succ("");
    }
}
