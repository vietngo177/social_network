package com.ghtk.social_network.mapper;

import com.ghtk.social_network.application.request.RegisterRequest;
import com.ghtk.social_network.domain.model.Role;
import com.ghtk.social_network.domain.model.User;
import com.ghtk.social_network.infrastracture.entity.RoleEntity;
import com.ghtk.social_network.infrastracture.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(RegisterRequest registerRequest);

    User toUser(UserEntity userEntity);

    UserEntity toUserEntity(User user);

    default Role toRole(RoleEntity roleEntity){
        return roleEntity.getRoleName();
    }

    default RoleEntity toRoleEntity(Role role){
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRoleName(role);
        return roleEntity;
    }
}
