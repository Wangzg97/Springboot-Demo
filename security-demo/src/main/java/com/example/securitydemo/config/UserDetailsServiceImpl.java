package com.example.securitydemo.config;

import com.example.securitydemo.entity.SysUser;
import com.example.securitydemo.security.AccountUser;
import com.example.securitydemo.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.TreeSet;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.getByUsername(s);
        if (sysUser == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        return new AccountUser(sysUser.getId(), sysUser.getUsername(), sysUser.getPassword(), new TreeSet<>());
    }

    /**
     * 获取用户权限信息（角色、菜单权限）
     * @param userId
     * @return
     */
    public List<GrantedAuthority> getUserAuthority(Long userId){

        // 角色(ROLE_admin)、菜单操作权限 sys:user:list
//        String authority = sysUserService.getUserAuthorityInfo(userId);  // ROLE_admin,ROLE_normal,sys:user:list,....
        String authority = "admin";
        return AuthorityUtils.commaSeparatedStringToAuthorityList(authority);
    }
}
