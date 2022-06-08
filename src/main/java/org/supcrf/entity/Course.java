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
 * 课程信息
 * </p>
 *
 * @author supcrf
 * @since 2021-02-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 课程Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 课程真实Id
     */
    private String courseId;

    /**
     * 用户Id
     */
    private String userId;

    /**
     * 用户类型
     */
    private String userType;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 课程地址
     */
    private String courseUrl;

    /**
     * 课程状态
     */
    private Integer state;

    /**
     * 课程封面
     */
    private String coursePic;

    /**
     * 课程描述
     */
    private String title;

    /**
     * 课程创建时间
     */
    private LocalDateTime createTime;

    /**
     * 课程更新时间
     */
    private LocalDateTime updateTime;


}
