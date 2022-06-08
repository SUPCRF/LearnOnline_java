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
 * 学生信息
 * </p>
 *
 * @author supcrf
 * @since 2021-02-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class StudentUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 学生Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 学生真实Id
     */
    private String studentId;

    /**
     * 学生名称
     */
    private String username;

    /**
     * 学生密码
     */
    private String password;

    /**
     * 学生状态
     */
    private Integer state;

    /**
     * 学生性别
     */
    private Integer sex;

    /**
     * 学生头像
     */
    private String avatar;

    /**
     * 学生电话号码
     */
    private String phone;

    /**
     * 学生电子邮箱
     */
    private String email;

    /**
     * 学生出生日期
     */
    private LocalDateTime birth;

    /**
     * 学生创建时间
     */
    private LocalDateTime createTime;

    /**
     * 学生更新时间
     */
    private LocalDateTime updateTime;


}
