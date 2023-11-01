package com.xiaomizhou.admin.user;

import com.xiaomizhou.admin.dept.Department;
import com.xiaomizhou.admin.role.Role;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class UserResponse implements Serializable {
    private Integer id;
    private String username;
    private Department department;
    private String name;
    private Short sex;
    private LocalDate birthday;
    private String email;
    private Short status;
    private Set<Role> role;
}
