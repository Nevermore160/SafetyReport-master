package com.lt.utils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DataResultUtil<T> {

    /**
     * 响应状态码
     */
    @ApiModelProperty(value = "响应状态码")
    private int code;

    /**
     * 响应提示语
     */
    @ApiModelProperty(value = "响应提示语")
    private String msg;

    /**
     * 响应数据
     */
    @ApiModelProperty(value = "响应数据")
    private T data;

    public DataResultUtil(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public DataResultUtil(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public DataResultUtil(int code, T data) {
        this.code = code;
        this.data = data;
    }
}
