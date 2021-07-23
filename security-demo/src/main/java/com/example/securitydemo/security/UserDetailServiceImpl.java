package com.example.securitydemo.security;

import com.example.securitydemo.entity.SysUser;
import com.example.securitydemo.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: wangzg
 * @Time: 2021/7/23
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //根据用户名查询用户的信息
        SysUser sysUser = sysUserService.getByUsername(username);
        //获取用户的相关权限信息
        String authority = sysUserService.getAuthorityByUsername(username);

        User user = new User(sysUser.getUsername(), sysUser.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList(authority));
        return user;
    }

}
