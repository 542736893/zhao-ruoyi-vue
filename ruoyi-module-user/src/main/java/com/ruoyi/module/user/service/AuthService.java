package com.ruoyi.module.user.service;

import com.ruoyi.module.user.dto.LoginRequest;
import com.ruoyi.module.user.dto.LoginResponse;

/**
 * 认证服务接口
 * 
 * @author ruoyi
 */
public interface AuthService {

    /**
     * 用户登录
     * 
     * @param loginRequest 登录请求
     * @return 登录响应
     */
    LoginResponse login(LoginRequest loginRequest);

    /**
     * 用户登出
     * 
     * @param token JWT Token
     * @return 是否成功
     */
    boolean logout(String token);

    /**
     * 刷新令牌
     * 
     * @param refreshToken 刷新令牌
     * @return 新的访问令牌
     */
    LoginResponse refreshToken(String refreshToken);

    /**
     * 验证令牌
     * 
     * @param token JWT Token
     * @return 是否有效
     */
    boolean validateToken(String token);
}
