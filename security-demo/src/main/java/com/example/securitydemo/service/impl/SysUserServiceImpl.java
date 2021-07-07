package com.example.securitydemo.service.impl;

import com.example.securitydemo.entity.SysUser;
import com.example.securitydemo.service.SysUserService;
import org.springframework.stereotype.Service;

@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {
    @Override
    public SysUser getByUsername(String username) {
        return new SysUser(1L,"admin", "$2a$10$R7zegeWzOXPw871CmNuJ6upC0v8D373GuLuTw8jn6NET4BkPRZfgK");
    }
}
