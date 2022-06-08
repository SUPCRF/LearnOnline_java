package org.supcrf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 教师信息
 * </p>
 *
 * @author supcrf
 * @since 2021-02-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TeacherUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 教师Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 教师真实Id
     */
    private String teacherId;

    /**
     * 教师名称
     */
    private String username;

    /**
     * 教师密码
     */
    private String password;

    /**
     * 教师状态
     */
    private Integer state;

    /**
     * 教师性别
     */
    private Integer sex;

    /**
     * 教师头像
     */
    private String avatar;

    /**
     * 教师电话号码
     */
    private String phone;

    /**
     * 教师电子邮箱
     */
    private String email;

    /**
     * 教师出生日期
     */
    private LocalDateTime birth;

    /**
     * 教师创建时间
     */
    private LocalDateTime createTime;

    /**
     * 教师更新时间
     */
    private LocalDateTime updateTime;


}
