package com.ruoyi.module.user.service.impl;

import com.ruoyi.module.user.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现类
 * 
 * @author ruoyi
 */
@Service
public class UserServiceImpl implements UserService {
    
    @Override
    public String getUserInfo() {
        return "用户模块服务运行正常";
    }
} 