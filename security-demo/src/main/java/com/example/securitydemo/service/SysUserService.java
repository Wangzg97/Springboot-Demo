package com.example.securitydemo.service;

import com.example.securitydemo.entity.SysUser;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: wangzg
 * @Time: 2021/7/23
 */
@Service
public class SysUserService {

    /**
     * 根据用户名查询用户信息，实际应该从数据库用户表中查询，用户的密码字段存储的应该是加密字符串
     * @param username 用户名
     * @return SysUser
     */
    public SysUser getByUsername(String username){
        //password为123456
        return new SysUser("admin", "$2a$10$U48gzPXkDwms.7IDP7MiOuLDf8Xgwt47itMuAqLpvMiqgvyagjQpe");
    }

    /**
     * 根据用户名查询用户的权限信息，包括角色信息和操作权限，角色信息中应加上前缀 ROLE_ security才能识别为角色其他的为操作权限，实际应该从数据库表中查询
     * @param username 用户名
     * @return SysUser
     */
    public String getAuthorityByUsername(String username){
        return "ROLE_admin,add,delete,update,query";
    }

}
