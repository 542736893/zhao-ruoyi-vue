package com.ruoyi.module.user.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;

/**
 * 用户对象 user
 *
 * @author ruoyi
 */
@Data
@AllArgsConstructor
@Schema(description = "用户信息")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 用户ID */
    @Schema(description = "用户ID")
    private Long id;

    /** 用户名 */
    @Schema(description = "用户名")
    private String username;

    /** 年龄 */
    @Schema(description = "年龄")
    private Integer age;

    // 无参构造函数
    public User() {
    }

    // 添加一个带 id 参数的构造函数，用于兼容现有代码
    public User(Long id) {
        this.id = id;
    }

    // 手动添加 getter 和 setter 方法（Lombok 可能没有正确工作）
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", age=" + age +
                '}';
    }
}
