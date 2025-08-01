package com.ruoyi.module.user.controller;

import com.ruoyi.framework.security.JwtAuthenticationFilter;
import com.ruoyi.framework.utils.CommonResult;
import com.ruoyi.module.user.dto.LoginRequest;
import com.ruoyi.module.user.dto.LoginResponse;
import com.ruoyi.module.user.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录认证控制器
 *
 * @author ruoyi
 */
@Tag(name = "认证管理", description = "用户登录、登出、令牌刷新等认证相关接口")
@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private AuthService authService;

    /**
     * 用户登录
     */
    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "用户名密码登录，返回 JWT 令牌")
    public CommonResult<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            System.out.println("收到登录请求: " + loginRequest.getUsername());
            LoginResponse loginResponse = authService.login(loginRequest);
            System.out.println("登录响应: " + loginResponse);
            return CommonResult.success(loginResponse, "登录成功");
        } catch (Exception e) {
            System.out.println("登录异常: " + e.getMessage());
            e.printStackTrace();
            return CommonResult.error(400, "登录失败: " + e.getMessage());
        }
    }

    /**
     * 用户登出
     */
    @PostMapping("/logout")
    @Operation(summary = "用户登出", description = "用户登出，使令牌失效")
    public CommonResult<String> logout(HttpServletRequest request) {
        try {
            String token = getTokenFromRequest(request);
            if (StringUtils.hasText(token)) {
                authService.logout(token);
            }

            // 清除安全上下文
            SecurityContextHolder.clearContext();

            return CommonResult.success(null, "登出成功");
        } catch (Exception e) {
            return CommonResult.error(400, "登出失败: " + e.getMessage());
        }
    }

    /**
     * 刷新令牌
     */
    @PostMapping("/refresh")
    @Operation(summary = "刷新令牌", description = "使用刷新令牌获取新的访问令牌")
    public CommonResult<LoginResponse> refreshToken(
            @Parameter(description = "刷新令牌") @RequestParam String refreshToken) {
        try {
            LoginResponse loginResponse = authService.refreshToken(refreshToken);
            return CommonResult.success(loginResponse, "令牌刷新成功");
        } catch (Exception e) {
            return CommonResult.error(400, "令牌刷新失败: " + e.getMessage());
        }
    }

    /**
     * 验证令牌
     */
    @GetMapping("/validate")
    @Operation(summary = "验证令牌", description = "验证当前令牌是否有效")
    public CommonResult<Map<String, Object>> validateToken(HttpServletRequest request) {
        try {
            String token = getTokenFromRequest(request);
            if (!StringUtils.hasText(token)) {
                return CommonResult.error(400, "令牌不能为空");
            }

            boolean isValid = authService.validateToken(token);
            Map<String, Object> result = new HashMap<>();
            result.put("valid", isValid);

            if (isValid) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication != null && authentication.getPrincipal() instanceof JwtAuthenticationFilter.UserPrincipal) {
                    JwtAuthenticationFilter.UserPrincipal userPrincipal =
                        (JwtAuthenticationFilter.UserPrincipal) authentication.getPrincipal();
                    result.put("userId", userPrincipal.getUserId());
                    result.put("username", userPrincipal.getUsername());
                    result.put("authorities", authentication.getAuthorities());
                }
            }

            return CommonResult.success(result, "令牌验证完成");
        } catch (Exception e) {
            return CommonResult.error(400, "令牌验证失败: " + e.getMessage());
        }
    }

    /**
     * 获取当前用户信息
     */
    @GetMapping("/me")
    @Operation(summary = "获取当前用户信息", description = "获取当前登录用户的基本信息")
    public CommonResult<Map<String, Object>> getCurrentUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getPrincipal() instanceof JwtAuthenticationFilter.UserPrincipal) {
                JwtAuthenticationFilter.UserPrincipal userPrincipal =
                    (JwtAuthenticationFilter.UserPrincipal) authentication.getPrincipal();

                Map<String, Object> userInfo = new HashMap<>();
                userInfo.put("userId", userPrincipal.getUserId());
                userInfo.put("username", userPrincipal.getUsername());
                userInfo.put("authorities", authentication.getAuthorities());

                return CommonResult.success(userInfo, "获取用户信息成功");
            }

            return CommonResult.error(401, "用户未登录");
        } catch (Exception e) {
            return CommonResult.error(400, "获取用户信息失败: " + e.getMessage());
        }
    }

    /**
     * 管理员专用接口示例
     */
    @GetMapping("/admin/info")
    @Operation(summary = "管理员信息", description = "仅管理员可访问的接口示例")
    @PreAuthorize("hasRole('ADMIN')")
    public CommonResult<String> getAdminInfo() {
        return CommonResult.success("这是管理员专用信息", "管理员接口访问成功");
    }

    /**
     * 从请求中获取 JWT Token
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
