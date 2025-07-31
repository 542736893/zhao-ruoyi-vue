package com.ruoyi.framework.utils;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;

/**
 * 通用响应封装类
 *
 * @param <T> 数据类型
 * @author ruoyi
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "通用响应结果")
public class CommonResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 响应码
     */
    @Schema(description = "响应码")
    private Integer code;

    /**
     * 响应消息
     */
    @Schema(description = "响应消息")
    private String msg;

    /**
     * 响应数据
     */
    @Schema(description = "响应数据")
    private T data;

    /**
     * 成功响应（无数据）
     *
     * @param msg 响应消息
     * @return CommonResult
     */
    public static <T> CommonResult<T> success(String msg) {
        return new CommonResult<>(200, msg, null);
    }

    /**
     * 成功响应（有数据）
     *
     * @param data 响应数据
     * @param msg  响应消息
     * @return CommonResult
     */
    public static <T> CommonResult<T> success(T data, String msg) {
        return new CommonResult<>(200, msg, data);
    }

    /**
     * 失败响应
     *
     * @param code 响应码
     * @param msg  响应消息
     * @return CommonResult
     */
    public static <T> CommonResult<T> error(Integer code, String msg) {
        return new CommonResult<>(code, msg, null);
    }

    /**
     * 失败响应（默认500错误码）
     *
     * @param msg 响应消息
     * @return CommonResult
     */
    public static <T> CommonResult<T> error(String msg) {
        return new CommonResult<>(500, msg, null);
    }
}