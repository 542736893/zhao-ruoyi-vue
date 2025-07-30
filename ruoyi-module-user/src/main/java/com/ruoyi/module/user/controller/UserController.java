package com.ruoyi.module.user.controller;

import com.ruoyi.module.user.domain.User;
import com.ruoyi.module.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户管理控制器
 *
 * @author ruoyi
 */
@Tag(name = "用户管理", description = "用户管理相关接口")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 获取用户信息
     */
    @GetMapping("/info")
    @Operation(summary = "获取用户信息")
    public Map<String, Object> getUserInfo() {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("msg", "获取用户信息成功");
        result.put("data", userService.getUserInfo());
        result.put("total", userService.countUsers());
        return result;
    }

    /**
     * 获取用户列表
     */
    @GetMapping("/list")
    @Operation(summary = "获取用户列表")
    public Map<String, Object> list(User user) {
        List<User> list = userService.selectUserList(user);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("msg", "查询成功");
        result.put("rows", list);
        result.put("total", list.size());
        return result;
    }

    /**
     * 根据用户编号获取详细信息
     */
    @GetMapping(value = "/{id}")
    @Operation(summary = "根据用户编号获取详细信息")
    public Map<String, Object> getInfo(@Parameter(description = "用户ID") @PathVariable Long id) {
        User user = userService.selectUserById(id);
        Map<String, Object> result = new HashMap<>();
        result.put("code", user != null ? 200 : 404);
        result.put("msg", user != null ? "查询成功" : "用户不存在");
        result.put("data", user);
        return result;
    }

    /**
     * 新增用户
     */
    @PostMapping
    @Operation(summary = "新增用户")
    public Map<String, Object> add(@RequestBody User user) {
        Map<String, Object> result = new HashMap<>();

        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            result.put("code", 400);
            result.put("msg", "用户名不能为空");
            return result;
        }

        if (!userService.checkUsernameUnique(user.getUsername())) {
            result.put("code", 500);
            result.put("msg", "新增用户'" + user.getUsername() + "'失败，用户名已存在");
            return result;
        }

        int rows = userService.insertUser(user);
        result.put("code", rows > 0 ? 200 : 500);
        result.put("msg", rows > 0 ? "新增成功" : "新增失败");
        if (rows > 0) {
            result.put("data", user);
        }
        return result;
    }

    /**
     * 修改用户
     */
    @PutMapping
    @Operation(summary = "修改用户")
    public Map<String, Object> edit(@RequestBody User user) {
        Map<String, Object> result = new HashMap<>();

        if (user.getId() == null) {
            result.put("code", 400);
            result.put("msg", "用户ID不能为空");
            return result;
        }

        User existUser = userService.selectUserById(user.getId());
        if (existUser == null) {
            result.put("code", 404);
            result.put("msg", "用户不存在");
            return result;
        }

        // 如果修改了用户名，需要检查唯一性
        if (user.getUsername() != null && !user.getUsername().equals(existUser.getUsername())) {
            if (!userService.checkUsernameUnique(user.getUsername())) {
                result.put("code", 500);
                result.put("msg", "修改用户失败，用户名已存在");
                return result;
            }
        }

        int rows = userService.updateUser(user);
        result.put("code", rows > 0 ? 200 : 500);
        result.put("msg", rows > 0 ? "修改成功" : "修改失败");
        return result;
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除用户")
    public Map<String, Object> remove(@Parameter(description = "用户ID数组") @PathVariable Long[] ids) {
        int rows = userService.deleteUserByIds(ids);
        Map<String, Object> result = new HashMap<>();
        result.put("code", rows > 0 ? 200 : 500);
        result.put("msg", rows > 0 ? "删除成功" : "删除失败");
        return result;
    }

    /**
     * 统计用户总数
     */
    @GetMapping("/count")
    @Operation(summary = "统计用户总数")
    public Map<String, Object> count() {
        int total = userService.countUsers();
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("msg", "统计成功");
        result.put("data", total);
        return result;
    }
}