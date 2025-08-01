package com.ruoyi.module.user.service;

import com.ruoyi.module.user.domain.Resource;

import java.util.List;

/**
 * 资源服务接口
 * 
 * @author ruoyi
 */
public interface ResourceService {
    
    /**
     * 查询资源列表
     * 
     * @param resource 资源信息
     * @return 资源信息集合
     */
    List<Resource> selectResourceList(Resource resource);

    /**
     * 通过资源ID查询资源
     * 
     * @param resourceId 资源ID
     * @return 资源对象信息
     */
    Resource selectResourceById(Long resourceId);

    /**
     * 通过资源编码查询资源
     * 
     * @param code 资源编码
     * @return 资源对象信息
     */
    Resource selectResourceByCode(String code);

    /**
     * 校验资源编码是否唯一
     * 
     * @param code 资源编码
     * @return 结果
     */
    boolean checkCodeUnique(String code);

    /**
     * 新增资源信息
     * 
     * @param resource 资源信息
     * @return 结果
     */
    int insertResource(Resource resource);

    /**
     * 修改资源信息
     * 
     * @param resource 资源信息
     * @return 结果
     */
    int updateResource(Resource resource);

    /**
     * 通过资源ID删除资源
     * 
     * @param resourceId 资源ID
     * @return 结果
     */
    int deleteResourceById(Long resourceId);

    /**
     * 批量删除资源信息
     * 
     * @param resourceIds 需要删除的资源ID
     * @return 结果
     */
    int deleteResourceByIds(Long[] resourceIds);

    /**
     * 统计资源总数
     * 
     * @return 资源总数
     */
    int countResources();

    /**
     * 根据用户ID查询用户拥有的资源
     * 
     * @param userId 用户ID
     * @return 资源列表
     */
    List<Resource> selectResourcesByUserId(Long userId);
}
