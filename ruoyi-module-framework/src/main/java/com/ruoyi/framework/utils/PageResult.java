package com.ruoyi.framework.utils;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 分页结果类
 * 
 * @author ruoyi
 */
@Data
@Schema(description = "分页结果")
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 数据列表
     */
    @Schema(description = "数据列表")
    private List<T> list = Collections.emptyList();

    /**
     * 总记录数
     */
    @Schema(description = "总记录数")
    private Long total = 0L;

    /**
     * 构造函数
     */
    public PageResult() {
    }

    /**
     * 构造函数
     * 
     * @param list 数据列表
     * @param total 总记录数
     */
    public PageResult(List<T> list, Long total) {
        this.list = list != null ? list : Collections.emptyList();
        this.total = total != null ? total : 0L;
    }

    /**
     * 创建空的分页结果
     * 
     * @param <T> 数据类型
     * @return 空的分页结果
     */
    public static <T> PageResult<T> empty() {
        return new PageResult<>();
    }

    /**
     * 创建分页结果
     * 
     * @param list 数据列表
     * @param total 总记录数
     * @param <T> 数据类型
     * @return 分页结果
     */
    public static <T> PageResult<T> of(List<T> list, Long total) {
        return new PageResult<>(list, total);
    }
}