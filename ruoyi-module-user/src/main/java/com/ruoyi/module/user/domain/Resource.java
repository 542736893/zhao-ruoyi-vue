package com.ruoyi.module.user.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;

/**
 * 资源对象 resource
 * 
 * @author ruoyi
 */
@Data
@Schema(description = "资源信息")
public class Resource implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /** 资源ID */
    @Schema(description = "资源ID")
    private Long resourceId;

    /** 资源编码 */
    @Schema(description = "资源编码")
    private String code;

    /**
     * 无参构造函数
     */
    public Resource() {
    }

    /**
     * 带 resourceId 参数的构造函数
     */
    public Resource(Long resourceId) {
        this.resourceId = resourceId;
    }

    /**
     * 全参构造函数
     */
    public Resource(Long resourceId, String code) {
        this.resourceId = resourceId;
        this.code = code;
    }

    // 手动添加 getter 和 setter 方法
    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "resourceId=" + resourceId +
                ", code='" + code + '\'' +
                '}';
    }
}
