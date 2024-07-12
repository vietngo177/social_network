package com.ghtk.social_network.util.mapper;

import com.ghtk.social_network.domain.model.UserDomain;
import com.ghtk.social_network.infrastracture.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final ModelMapper modelMapper;
    public UserDomain toUserDomain(UserEntity userEntity){
        UserDomain userDomain = this.modelMapper.map(userEntity, UserDomain.class);
        return userDomain;
    }

    public UserEntity toUserEntity(UserDomain userDomain){
        UserEntity userEntity = this.modelMapper.map(userDomain, UserEntity.class);
        return userEntity;
    }
}
