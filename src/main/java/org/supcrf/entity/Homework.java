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
 * 作业信息
 * </p>
 *
 * @author supcrf
 * @since 2021-02-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Homework implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 作业Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 作业真实Id
     */
    private String homeworkId;

    /**
     * 课程Id
     */
    private String courseId;

    /**
     * 作业名称
     */
    private String homeworkName;

    /**
     * 作业内容
     */
    private Integer courseContent;

    /**
     * 作业状态
     */
    private Integer state;

    /**
     * 作业描述
     */
    private String title;

    /**
     * 作业创建时间
     */
    private LocalDateTime createTime;

    /**
     * 作业更新时间
     */
    private LocalDateTime updateTime;


}
