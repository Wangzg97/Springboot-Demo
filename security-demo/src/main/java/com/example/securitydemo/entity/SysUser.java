package com.example.securitydemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 系统用户，数据库读取
 * @Author: wangzg
 * @Time: 2021/7/23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUser {
    private String username;
    private String password;
}
