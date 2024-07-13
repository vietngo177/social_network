package com.ghtk.social_network.util.mapper;

import com.ghtk.social_network.application.request.RegisterRequest;
import com.ghtk.social_network.util.constant.Role;
import com.ghtk.social_network.domain.model.User;
import com.ghtk.social_network.infrastracture.entity.RoleEntity;
import com.ghtk.social_network.infrastracture.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(RegisterRequest registerRequest);

    User toUser(UserEntity userEntity);

    UserEntity toUserEntity(User user);
}
