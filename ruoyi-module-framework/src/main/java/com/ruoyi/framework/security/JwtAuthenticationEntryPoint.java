package com.ruoyi.framework.security;

import com.alibaba.fastjson.JSON;
import com.ruoyi.framework.utils.CommonResult;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * JWT 认证入口点
 * 处理未认证的请求
 * 
 * @author ruoyi
 */
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, 
                        AuthenticationException authException) throws IOException, ServletException {
        
        // 设置响应状态码
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        
        // 设置响应内容类型
        response.setContentType("application/json;charset=UTF-8");
        
        // 创建错误响应
        CommonResult<String> result = CommonResult.error(401, "未认证，请先登录");
        
        // 写入响应
        response.getWriter().write(JSON.toJSONString(result));
        response.getWriter().flush();
    }
}
