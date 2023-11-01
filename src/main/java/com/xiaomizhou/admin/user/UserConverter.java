package com.xiaomizhou.admin.user;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author xiaomizhou
 * @date 2023/10/27
 * @email 521jx123@gmail.com
 */
@Mapper
public interface UserConverter {

    UserConverter INSTANCES = Mappers.getMapper(UserConverter.class);

    User converterReq2Entity(UserRequest request);

    UserResponse convertEntity2Resp(User user);
}
