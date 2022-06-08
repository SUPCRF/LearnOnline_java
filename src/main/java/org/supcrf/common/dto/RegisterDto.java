package org.supcrf.common.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @title: RegisterDto
 * @projectName learn-online_java
 * @description: TODO
 * @Author supcrf
 * @Version 1.0
 * @Date 2/24/2021 01:04
 */
@Data
public class RegisterDto implements Serializable {
    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 类别
     */
    @NotBlank(message = "邮箱不能为空")
    private String email;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;
}
