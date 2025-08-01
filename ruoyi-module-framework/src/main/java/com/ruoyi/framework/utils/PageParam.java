package com.ruoyi.framework.utils;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * 分页参数类
 * 
 * @author ruoyi
 */
@Data
@Schema(description = "分页参数")
public class PageParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 当前页码
     */
    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码必须大于0")
    @Schema(description = "当前页码", example = "1")
    private Integer pageNum = 1;

    /**
     * 每页显示数量
     */
    @NotNull(message = "每页数量不能为空")
    @Min(value = 1, message = "每页数量必须大于0")
    @Schema(description = "每页显示数量", example = "10")
    private Integer pageSize = 10;

    /**
     * 获取偏移量
     * 
     * @return 偏移量
     */
    public int getOffset() {
        return (pageNum - 1) * pageSize;
    }
}