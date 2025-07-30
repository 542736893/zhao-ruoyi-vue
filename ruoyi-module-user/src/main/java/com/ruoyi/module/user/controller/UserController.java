package com.ruoyi.module.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户管理控制器
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/user")
public class UserController {
    
    /**
     * 获取用户信息
     */
    @GetMapping("/info")
    public Map<String, Object> getUserInfo() {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("msg", "获取用户信息成功");
        result.put("data", "用户模块运行正常");
        return result;
    }
} 