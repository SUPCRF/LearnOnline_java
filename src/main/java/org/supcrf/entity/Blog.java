package org.supcrf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.sql.Blob;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 博客信息
 * </p>
 *
 * @author supcrf
 * @since 2021-02-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Blog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 博客Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 博客真实Id
     */
    private String blogId;

    /**
     * 用户Id
     */
    private String userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 博客名称
     */
    private String blogName;

    /**
     * 博客地址
     */
    private String blogUrl;

    /**
     * 博客状态
     */
    private Integer state;

    /**
     * 博客封面
     */
    private String blogPic;

    /**
     * 博客描述
     */
    private String title;

    /**
     * 博客创建时间
     */
    private LocalDateTime createTime;

    /**
     * 博客更新时间
     */
    private LocalDateTime updateTime;

    /**
     * md内容
     */
    private String md;

    /**
     * html内容
     */
    private String html;


}
