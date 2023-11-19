package com.xiaomizhou.admin.interfaces.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhangyaxi
 * @email 521jx123@gmail.com
 * @date 2023/11/17
 */
@Data
@Schema(description = "部门查询VO类")
public class DepartmentQueryVo implements Serializable {
    @Schema(description = "部门编码")
    private String code;
    @Schema(description = "部门名称")
    private String name;
}
