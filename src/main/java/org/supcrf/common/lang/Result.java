package org.supcrf.common.lang;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @title: Result
 * @projectName learn-online_java
 * @description: TODO
 * @Author supcrf
 * @Version 1.0
 * @Date 2/15/2021 19:22
 */
@Data
public class Result<T> {

    @ApiModelProperty(value = "返回码", name = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回信息提示", name = "返回信息提示")
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "数据对象", name = "数据对象")
    private T data;

    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

}


