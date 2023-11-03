package com.xiaomizhou.admin.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Map;

/**
 * @author xiaomizhou
 * @date 2023/10/27
 * @email 521jx123@gmail.com
 */
@Mapper
public interface UserConverter {

    UserConverter INSTANCES = Mappers.getMapper(UserConverter.class);

    @Mapping(source = "deptId", target = "department.id")
    User converterReq2Entity(UserRequest request);

    UserResponse convertEntity2Resp(User user);
}
