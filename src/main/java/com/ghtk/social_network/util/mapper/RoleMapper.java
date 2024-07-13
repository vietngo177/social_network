package com.ghtk.social_network.util.mapper;

import com.ghtk.social_network.domain.model.RoleDomain;
import com.ghtk.social_network.domain.model.UserDomain;
import com.ghtk.social_network.infrastracture.entity.RoleEntity;
import com.ghtk.social_network.infrastracture.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleMapper {
    private final ModelMapper modelMapper;
    public RoleDomain toRoleDomain(RoleEntity roleEntity){
        RoleDomain roleDomain = this.modelMapper.map(roleEntity, RoleDomain.class);
        return roleDomain;
    }

    public RoleEntity toRoleEntity(RoleDomain roleDomain){
        RoleEntity roleEntity = this.modelMapper.map(roleDomain, RoleEntity.class);
        return roleEntity;
    }
}
