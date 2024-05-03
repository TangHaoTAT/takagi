package com.tanghao.takagi.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description 通用返回对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "通用返回对象")
public class CommonResult {
    @Schema(name = "msg", description = "描述信息")
    private String msg;// 描述信息

    @Schema(name = "code", description = "状态码")
    private Integer code;// 状态码

    @Schema(name = "data", description = "携带对象")
    private Object data;// 携带对象

    // 预定的状态码
    public static final int CODE_SUCCESS = 200;
    public static final int CODE_ERROR = 500;

    public static CommonResult ok(){
        return new CommonResult("ok", CODE_SUCCESS, null);
    }

    public static CommonResult ok(Object data){
        return new CommonResult("ok", CODE_SUCCESS, data);
    }

    public static CommonResult error(){
        return new CommonResult("error", CODE_ERROR, null);
    }

    public static CommonResult error(String msg){
        return new CommonResult(msg, CODE_ERROR, null);
    }

    public static CommonResult get(String msg, Integer code, Object data){
        return new CommonResult(msg, code, data);
    }
}
