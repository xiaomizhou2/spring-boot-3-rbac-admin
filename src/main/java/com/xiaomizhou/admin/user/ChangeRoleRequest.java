package com.xiaomizhou.admin.user;

import com.xiaomizhou.admin.role.Role;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhangyaxi
 * @email 521jx123@gmail.com
 * @date 2023/11/4
 */
@Data
public class ChangeRoleRequest implements Serializable {

    private Integer id;

    private List<Role> roles;
}
