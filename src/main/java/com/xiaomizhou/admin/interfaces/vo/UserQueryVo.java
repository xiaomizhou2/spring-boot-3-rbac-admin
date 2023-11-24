package com.xiaomizhou.admin.interfaces.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhangyaxi
 * @email 521jx123@gmail.com
 * @date 2023/11/17
 */
@Data
@Schema(description = "用户查询VO类")
public class UserQueryVo implements Serializable {
    @Schema(description = "用户名")
    private String username;
    @Schema(description = "姓名")
    private String name;
    @Schema(description = "邮箱")
    private String email;
    @Schema(description = "部门ID")
    private Integer deptId;
}
