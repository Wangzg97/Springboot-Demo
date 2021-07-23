package com.example.securitydemo.security;

import cn.hutool.core.util.StrUtil;
import com.example.securitydemo.service.SysUserService;
import com.example.securitydemo.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Description: jwt认证过滤器
 * @Author: wangzg
 * @Time: 2021/7/23
 */
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    SysUserService sysUserService;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        //从请求头中获取jwt
        String jwtInfo = request.getHeader(jwtUtils.getHeader());
        if (StrUtil.isBlankOrUndefined(jwtInfo)) {
            chain.doFilter(request, response);
            return;
        }

        //解析jwt信息
        Claims claims =jwtUtils.getClaimByToken(jwtInfo);
        if (claims == null) {
            throw new JwtException("token异常");
        }
        if (jwtUtils.isTokenExpired(claims)) {
            throw new JwtException("token已过期");
        }

        //解析出用户名
        String username =claims.getSubject();
        //获取用户的相关权限信息
        String authority = sysUserService.getAuthorityByUsername(username);
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(authority);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, null, grantedAuthorities);
        //将token放入到security上下文中
        SecurityContextHolder.getContext().setAuthentication(token);

        chain.doFilter(request, response);
    }
}
