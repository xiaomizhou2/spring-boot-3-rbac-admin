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
@Schema(description = "api查询VO类")
public class ApiQueryVo implements Serializable {
    @Schema(description = "请求路径")
    private String url;
    @Schema(description = "请求方式")
    private String method;
}
