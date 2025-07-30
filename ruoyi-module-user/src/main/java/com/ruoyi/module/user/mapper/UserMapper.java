package com.ruoyi.module.user.mapper;

import com.ruoyi.module.user.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户表 数据层
 *
 * @author ruoyi
 */
@Mapper
public interface UserMapper {

    /**
     * 查询用户列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    List<User> selectUserList(User user);

    /**
     * 通过用户名查询用户
     *
     * @param username 用户名
     * @return 用户对象信息
     */
    User selectUserByUsername(String username);

    /**
     * 通过用户ID查询用户
     *
     * @param id 用户ID
     * @return 用户对象信息
     */
    User selectUserById(Long id);

    /**
     * 新增用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    int insertUser(User user);

    /**
     * 修改用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    int updateUser(User user);

    /**
     * 通过用户ID删除用户
     *
     * @param id 用户ID
     * @return 结果
     */
    int deleteUserById(Long id);

    /**
     * 批量删除用户信息
     *
     * @param ids 需要删除的用户ID
     * @return 结果
     */
    int deleteUserByIds(Long[] ids);

    /**
     * 校验用户名称是否唯一
     *
     * @param username 用户名称
     * @return 结果
     */
    User checkUsernameUnique(String username);

    /**
     * 统计用户总数
     *
     * @return 用户总数
     */
    int countUsers();
}
