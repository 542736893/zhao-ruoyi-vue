package com.ruoyi.framework.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 日期工具类
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtils {
    
    /**
     * 默认日期时间格式
     */
    public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    
    /**
     * 格式化当前日期时间
     * 
     * @return 格式化后的日期时间字符串
     */
    public static String formatNow() {
        return formatNow(DEFAULT_PATTERN);
    }
    
    /**
     * 根据指定格式格式化当前日期时间
     * 
     * @param pattern 日期时间格式
     * @return 格式化后的日期时间字符串
     */
    public static String formatNow(String pattern) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
    }
}