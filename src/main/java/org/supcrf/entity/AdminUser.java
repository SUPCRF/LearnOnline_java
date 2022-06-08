package org.supcrf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.bytedeco.javacpp.presets.opencv_core;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 管理员信息
 * </p>
 *
 * @author supcrf
 * @since 2021-02-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AdminUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 管理员Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 管理员真实Id
     */
    private String adminId;

    /**
     * 管理员名称
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 管理员密码
     */
    private String password;

    /**
     * 管理员状态
     */
    private Integer state;

    /**
     * 管理员性别
     */
    private Integer sex;

    /**
     * 管理员头像
     */
    private String avatar;

    /**
     * 管理员电话号码
     */
    private String phone;

    /**
     * 管理员电子邮箱
     */
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    /**
     * 管理员出生日期
     */
    private LocalDateTime birth;

    /**
     * 管理员创建时间
     */
    private LocalDateTime createTime;

    /**
     * 管理员更新时间
     */
    private LocalDateTime updateTime;


}
