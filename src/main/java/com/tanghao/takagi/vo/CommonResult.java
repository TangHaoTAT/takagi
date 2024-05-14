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
    @Schema(name = "code", description = "状态码")
    private Integer code;// 状态码

    @Schema(name = "msg", description = "描述信息")
    private String msg;// 描述信息

    @Schema(name = "data", description = "携带对象")
    private Object data;// 携带对象

    // 预定的状态码
    public static final int CODE_SUCCESS = 200;
    public static final int CODE_ERROR = 500;

    // 构建成功
    public static CommonResult ok() {
        return new CommonResult(CODE_SUCCESS, "ok", null);
    }

    public static CommonResult ok(String msg) {
        return new CommonResult(CODE_SUCCESS, msg, null);
    }

    public static CommonResult data(Object data) {
        return new CommonResult(CODE_SUCCESS, "ok", data);
    }

    // 构建失败
    public static CommonResult error() {
        return new CommonResult(CODE_ERROR, "error", null);
    }

    public static CommonResult error(String msg) {
        return new CommonResult(CODE_ERROR, msg, null);
    }

    // 构建指定状态码
    public static CommonResult get(Integer code, String msg, Object data) {
        return new CommonResult(code, msg, data);
    }

    @Override
    public String toString() {
        return "{"
                + "\"code\": " + this.getCode()
                + ", \"msg\": " + transValue(this.getMsg())
                + ", \"data\": " + transValue(this.getData())
                + "}";
    }

    /**
     * 转换 value 值：
     * 	如果 value 值属于 String 类型，则在前后补上引号
     * 	如果 value 值属于其它类型，则原样返回
     * @param value 具体要操作的值
     */
    private String transValue(Object value) {
        if(value == null) {
            return null;
        }
        if(value instanceof String) {
            return "\"" + value + "\"";
        }
        return String.valueOf(value);
    }
}
