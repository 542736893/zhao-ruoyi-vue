package com.ruoyi.framework.security;

import com.ruoyi.framework.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JWT 认证过滤器
 * 
 * @author ruoyi
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * JWT Token 请求头名称
     */
    private static final String AUTHORIZATION_HEADER = "Authorization";

    /**
     * JWT Token 前缀
     */
    private static final String TOKEN_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException {
        
        // 从请求头中获取 JWT Token
        String token = getTokenFromRequest(request);
        
        if (StringUtils.hasText(token) && jwtUtils.validateToken(token)) {
            // 从 Token 中获取用户信息
            String username = jwtUtils.getUsernameFromToken(token);
            Long userId = jwtUtils.getUserIdFromToken(token);
            String authorities = jwtUtils.getAuthoritiesFromToken(token);
            
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // 创建认证对象
                UsernamePasswordAuthenticationToken authentication = createAuthentication(username, userId, authorities);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                // 设置到安全上下文中
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        
        filterChain.doFilter(request, response);
    }

    /**
     * 从请求中获取 JWT Token
     * 
     * @param request HTTP 请求
     * @return JWT Token
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    /**
     * 创建认证对象
     * 
     * @param username 用户名
     * @param userId 用户ID
     * @param authorities 权限字符串
     * @return 认证对象
     */
    private UsernamePasswordAuthenticationToken createAuthentication(String username, Long userId, String authorities) {
        // 解析权限
        List<SimpleGrantedAuthority> grantedAuthorities = parseAuthorities(authorities);
        
        // 创建用户主体对象
        UserPrincipal userPrincipal = new UserPrincipal(userId, username);
        
        return new UsernamePasswordAuthenticationToken(userPrincipal, null, grantedAuthorities);
    }

    /**
     * 解析权限字符串
     * 
     * @param authorities 权限字符串（逗号分隔）
     * @return 权限列表
     */
    private List<SimpleGrantedAuthority> parseAuthorities(String authorities) {
        if (!StringUtils.hasText(authorities)) {
            return List.of();
        }
        
        return Arrays.stream(authorities.split(","))
                .map(String::trim)
                .filter(StringUtils::hasText)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    /**
     * 用户主体类
     */
    public static class UserPrincipal {
        private final Long userId;
        private final String username;

        public UserPrincipal(Long userId, String username) {
            this.userId = userId;
            this.username = username;
        }

        public Long getUserId() {
            return userId;
        }

        public String getUsername() {
            return username;
        }

        @Override
        public String toString() {
            return username;
        }
    }
}
