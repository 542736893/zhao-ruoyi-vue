package com.ruoyi.module.user.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;

/**
 * 用户资源关联对象 user_resource
 * 
 * @author ruoyi
 */
@Data
@Schema(description = "用户资源关联信息")
public class UserResource implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @Schema(description = "主键ID")
    private Long id;

    /** 用户ID */
    @Schema(description = "用户ID")
    private Long userId;

    /** 资源ID */
    @Schema(description = "资源ID")
    private Long resourceId;

    /**
     * 无参构造函数
     */
    public UserResource() {
    }

    /**
     * 带 id 参数的构造函数
     */
    public UserResource(Long id) {
        this.id = id;
    }

    /**
     * 用户资源关联构造函数
     */
    public UserResource(Long userId, Long resourceId) {
        this.userId = userId;
        this.resourceId = resourceId;
    }

    /**
     * 全参构造函数
     */
    public UserResource(Long id, Long userId, Long resourceId) {
        this.id = id;
        this.userId = userId;
        this.resourceId = resourceId;
    }

    // 手动添加 getter 和 setter 方法
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    @Override
    public String toString() {
        return "UserResource{" +
                "id=" + id +
                ", userId=" + userId +
                ", resourceId=" + resourceId +
                '}';
    }
}
