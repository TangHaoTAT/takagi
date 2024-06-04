package com.tanghao.takagi.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description 通用分页数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "通用分页数据")
public class CommonPage<T> {
    @Schema(name = "currentNo", description = "第几页")
    private Long currentNo;// 第几页

    @Schema(name = "totalPages", description = "总页数")
    private Long totalPages;// 总页数

    @Schema(name = "pageSize", description = "每页显示条数")
    private Long pageSize;// 每页显示条数

    @Schema(name = "count", description = "总条数")
    private Long count;// 总条数

    @Schema(name = "data", description = "查询结果")
    private T data;// 查询结果

    public static <T> CommonPage<T> get(Long currentNo, Long pageSize, Long count, T data) {
        Long totalPages = count % pageSize > 0 ? (count / pageSize) + 1 : count / pageSize;// 获取总页数
        return new CommonPage<>(currentNo, totalPages, pageSize, count, data);
    }
}
