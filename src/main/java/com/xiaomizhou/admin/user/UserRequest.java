package com.xiaomizhou.admin.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class UserRequest implements Serializable {
    private Integer deptId;
    private String name;
    private Short sex;
    private LocalDate birthday;
    private String email;
    private Short status;
}
