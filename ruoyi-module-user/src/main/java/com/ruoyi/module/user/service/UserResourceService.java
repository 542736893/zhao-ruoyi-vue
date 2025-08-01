package com.ruoyi.module.user.service;

import com.ruoyi.module.user.domain.UserResource;

import java.util.List;

/**
 * 用户资源关联服务接口
 * 
 * @author ruoyi
 */
public interface UserResourceService {
    
    /**
     * 查询用户资源关联列表
     * 
     * @param userResource 用户资源关联信息
     * @return 用户资源关联集合
     */
    List<UserResource> selectUserResourceList(UserResource userResource);

    /**
     * 通过ID查询用户资源关联
     * 
     * @param id 主键ID
     * @return 用户资源关联对象
     */
    UserResource selectUserResourceById(Long id);

    /**
     * 根据用户ID查询用户资源关联
     * 
     * @param userId 用户ID
     * @return 用户资源关联列表
     */
    List<UserResource> selectUserResourceByUserId(Long userId);

    /**
     * 根据资源ID查询用户资源关联
     * 
     * @param resourceId 资源ID
     * @return 用户资源关联列表
     */
    List<UserResource> selectUserResourceByResourceId(Long resourceId);

    /**
     * 查询用户是否拥有指定资源
     * 
     * @param userId 用户ID
     * @param resourceId 资源ID
     * @return 是否拥有
     */
    boolean hasResource(Long userId, Long resourceId);

    /**
     * 新增用户资源关联
     * 
     * @param userResource 用户资源关联信息
     * @return 结果
     */
    int insertUserResource(UserResource userResource);

    /**
     * 批量新增用户资源关联
     * 
     * @param userResources 用户资源关联列表
     * @return 结果
     */
    int batchInsertUserResource(List<UserResource> userResources);

    /**
     * 为用户分配资源
     * 
     * @param userId 用户ID
     * @param resourceIds 资源ID数组
     * @return 结果
     */
    int assignResourcesToUser(Long userId, Long[] resourceIds);

    /**
     * 删除用户资源关联
     * 
     * @param id 主键ID
     * @return 结果
     */
    int deleteUserResourceById(Long id);

    /**
     * 根据用户ID删除用户资源关联
     * 
     * @param userId 用户ID
     * @return 结果
     */
    int deleteUserResourceByUserId(Long userId);

    /**
     * 根据资源ID删除用户资源关联
     * 
     * @param resourceId 资源ID
     * @return 结果
     */
    int deleteUserResourceByResourceId(Long resourceId);

    /**
     * 删除指定用户的指定资源关联
     * 
     * @param userId 用户ID
     * @param resourceId 资源ID
     * @return 结果
     */
    int deleteUserResourceByUserIdAndResourceId(Long userId, Long resourceId);

    /**
     * 批量删除用户资源关联
     * 
     * @param ids 需要删除的ID数组
     * @return 结果
     */
    int deleteUserResourceByIds(Long[] ids);

    /**
     * 统计用户资源关联总数
     * 
     * @return 总数
     */
    int countUserResources();

    /**
     * 统计用户拥有的资源数量
     * 
     * @param userId 用户ID
     * @return 资源数量
     */
    int countResourcesByUserId(Long userId);

    /**
     * 统计拥有指定资源的用户数量
     * 
     * @param resourceId 资源ID
     * @return 用户数量
     */
    int countUsersByResourceId(Long resourceId);
}
