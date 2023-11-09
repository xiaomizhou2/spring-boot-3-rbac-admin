package com.xiaomizhou.admin.domain.department;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xiaomizhou
 * @date 2023/10/27
 * @email 521jx123@gmail.com
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_department")
public class DepartmentEntity {
    @Id
    private Integer id;

    @NotEmpty(message = "部门编码不允许为空")
    private String code;

    @NotEmpty(message = "部门名称不允许为空")
    private String name;

    private Short orderNo;
}
