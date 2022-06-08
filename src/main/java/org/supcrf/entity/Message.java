package org.supcrf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 留言
 * </p>
 *
 * @author supcrf
 * @since 2021-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 留言ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 回复邮箱
     */
    private String email;

    /**
     * 留言内容
     */
    private String content;


}
