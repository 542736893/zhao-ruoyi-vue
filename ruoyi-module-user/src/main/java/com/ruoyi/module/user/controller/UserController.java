package com.ruoyi.module.user.controller;
import com.ruoyi.framework.utils.CommonResult;
import com.ruoyi.module.user.domain.User;
import com.ruoyi.module.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * 用户管理控制器
 *
 * @author ruoyi
 */
@Tag(name = "用户管理", description = "用户管理相关接口")
@RestController
@RequestMapping("/user")
@Valid
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 获取用户信息
     */
    @GetMapping("/info")
    @Operation(summary = "获取用户信息")
    public CommonResult<Object> getUserInfo() {
        return CommonResult.success(userService.getUserInfo(), "获取用户信息成功");
    }

    /**
     * 获取用户列表
     */
    @GetMapping("/list")
    @Operation(summary = "获取用户列表")
    public CommonResult<List<User>> list(User user) {
        List<User> list = userService.selectUserList(user);
        return CommonResult.success(list, "查询成功");
    }

    /**
     * 根据用户编号获取详细信息
     */
    @GetMapping(value = "/{id}")
    @Operation(summary = "根据用户编号获取详细信息")
    public CommonResult<User> getInfo(@Parameter(description = "用户ID") 
                                      @PathVariable 
                                      @NotNull(message = "用户ID不能为空") 
                                      @Min(value = 1, message = "用户ID必须大于0") Long id) {
        User user = userService.selectUserById(id);
        if (user != null) {
            return CommonResult.success(user, "查询成功");
        } else {
            return CommonResult.error(404, "用户不存在");
        }
    }

    /**
     * 新增用户
     */
    @PostMapping
    @Operation(summary = "新增用户")
    public CommonResult<User> add(@RequestBody @Valid User user) {
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            return CommonResult.error(400, "用户名不能为空");
        }

        if (!userService.checkUsernameUnique(user.getUsername())) {
            return CommonResult.error(500, "新增用户'" + user.getUsername() + "'失败，用户名已存在");
        }

        int rows = userService.insertUser(user);
        if (rows > 0) {
            return CommonResult.success(user, "新增成功");
        } else {
            return CommonResult.error("新增失败");
        }
    }

    /**
     * 修改用户
     */
    @PutMapping
    @Operation(summary = "修改用户")
    public CommonResult<User> edit(@RequestBody @Valid User user) {
        if (user.getId() == null) {
            return CommonResult.error(400, "用户ID不能为空");
        }

        User existUser = userService.selectUserById(user.getId());
        if (existUser == null) {
            return CommonResult.error(404, "用户不存在");
        }

        // 如果修改了用户名，需要检查唯一性
        if (user.getUsername() != null && !user.getUsername().equals(existUser.getUsername())) {
            if (!userService.checkUsernameUnique(user.getUsername())) {
                return CommonResult.error(500, "修改用户失败，用户名已存在");
            }
        }

        int rows = userService.updateUser(user);
        if (rows > 0) {
            return CommonResult.success(user, "修改成功");
        } else {
            return CommonResult.error("修改失败");
        }
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除用户")
    public CommonResult<Void> remove(@Parameter(description = "用户ID数组") 
                                      @PathVariable 
                                      @NotNull(message = "用户ID不能为空") Long[] ids) {
        int rows = userService.deleteUserByIds(ids);
        if (rows > 0) {
            return CommonResult.success(null, "删除成功");
        } else {
            return CommonResult.error("删除失败");
        }
    }

    /**
     * 统计用户总数
     */
    @GetMapping("/count")
    @Operation(summary = "统计用户总数")
    public CommonResult<Integer> count() {
        int total = userService.countUsers();
        return CommonResult.success(total, "统计成功");
    }
}