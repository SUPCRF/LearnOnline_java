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
 * 考试信息
 * </p>
 *
 * @author supcrf
 * @since 2021-02-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Exam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 考试Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 考试真实Id
     */
    private String examId;

    /**
     * 课程Id
     */
    private String courseId;

    /**
     * 考试名称
     */
    private String examName;

    /**
     * 考试内容
     */
    private Integer examContent;

    /**
     * 考试状态
     */
    private Integer state;

    /**
     * 考试描述
     */
    private String title;

    /**
     * 考试创建时间
     */
    private LocalDateTime createTime;

    /**
     * 考试更新时间
     */
    private LocalDateTime updateTime;


}
