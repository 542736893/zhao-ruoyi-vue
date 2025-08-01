package com.ruoyi.module.user.service.impl;

import com.ruoyi.framework.utils.JwtUtils;
import com.ruoyi.module.user.domain.User;
import com.ruoyi.module.user.dto.LoginRequest;
import com.ruoyi.module.user.dto.LoginResponse;
import com.ruoyi.module.user.service.AuthService;
import com.ruoyi.module.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 认证服务实现类
 * 
 * @author ruoyi
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${jwt.expiration:86400000}")
    private Long jwtExpiration;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        // 1. 验证用户名和密码
        User user = userService.selectUserByUsername(loginRequest.getUsername());
        System.out.println("查询到的用户: " + user);
        if (user == null) {
            throw new RuntimeException("用户名或密码错误");
        }

        // 验证密码（这里简化处理，暂时使用明文密码比较）
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }

        // 2. 获取用户权限（这里简化处理，实际应该从数据库查询）
        String authorities = getUserAuthorities(user);

        // 3. 生成 JWT Token
        String accessToken = jwtUtils.generateToken(user.getUsername(), user.getId(), authorities);
        String refreshToken = jwtUtils.generateRefreshToken(user.getUsername());

        // 4. 计算过期时间（秒）
        Long expiresIn = jwtExpiration / 1000;

        // 5. 构建响应
        return new LoginResponse(accessToken, refreshToken, expiresIn, 
                               user.getId(), user.getUsername(), authorities);
    }

    @Override
    public boolean logout(String token) {
        // 这里可以实现 Token 黑名单机制
        // 由于 JWT 是无状态的，简单的做法是在客户端删除 Token
        // 复杂的做法是维护一个 Token 黑名单
        return true;
    }

    @Override
    public LoginResponse refreshToken(String refreshToken) {
        try {
            // 验证刷新令牌
            if (!jwtUtils.validateToken(refreshToken)) {
                throw new RuntimeException("刷新令牌无效");
            }

            // 从刷新令牌中获取用户名
            String username = jwtUtils.getUsernameFromToken(refreshToken);
            
            // 获取用户信息
            User user = userService.selectUserByUsername(username);
            if (user == null) {
                throw new RuntimeException("用户不存在");
            }

            // 获取用户权限
            String authorities = getUserAuthorities(user);

            // 生成新的访问令牌
            String newAccessToken = jwtUtils.generateToken(user.getUsername(), user.getId(), authorities);
            String newRefreshToken = jwtUtils.generateRefreshToken(user.getUsername());

            // 计算过期时间
            Long expiresIn = jwtExpiration / 1000;

            return new LoginResponse(newAccessToken, newRefreshToken, expiresIn,
                                   user.getId(), user.getUsername(), authorities);

        } catch (Exception e) {
            throw new RuntimeException("刷新令牌失败: " + e.getMessage());
        }
    }

    @Override
    public boolean validateToken(String token) {
        return jwtUtils.validateToken(token);
    }

    /**
     * 获取用户权限
     * 这里简化处理，实际应该从数据库查询用户的角色和权限
     * 
     * @param user 用户
     * @return 权限字符串
     */
    private String getUserAuthorities(User user) {
        // 简化处理：根据用户名分配权限
        if ("admin".equals(user.getUsername())) {
            return "ROLE_ADMIN,ROLE_USER";
        } else {
            return "ROLE_USER";
        }
    }
}
