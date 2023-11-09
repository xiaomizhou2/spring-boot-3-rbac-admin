package com.xiaomizhou.admin.domain.role;

import com.xiaomizhou.admin.domain.permission.PermissionEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

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
@Table(name = "t_role")
public class RoleEntity {
    @Id
    private Integer id;

    @NotEmpty(message = "角色编号不允许为空")
    private String code;

    @NotEmpty(message = "角色名称不允许为空")
    private String name;

    private Short status;

    private Short orderNo;

    @ManyToMany
    @JoinTable(name = "t_role_permission",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<PermissionEntity> permissions;
}
