package com.example.securitydemo.service;

import com.example.securitydemo.entity.SysUser;

public interface SysUserService {
    SysUser getByUsername(String username);
}
