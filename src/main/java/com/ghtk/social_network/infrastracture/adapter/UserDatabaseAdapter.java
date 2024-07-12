package com.ghtk.social_network.infrastracture.adapter;

import com.ghtk.social_network.domain.model.UserDomain;
import com.ghtk.social_network.domain.port.spi.UserDatabasePort;
import com.ghtk.social_network.infrastracture.entity.UserEntity;
import com.ghtk.social_network.infrastracture.repository.UserRepository;
import com.ghtk.social_network.util.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDatabaseAdapter implements UserDatabasePort {

    private final UserRepository userRepository;

    private final UserMapper userMapper;
    @Override
    public UserDomain findUserByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);

        return userMapper.toUserDomain(userEntity);
    }
    @Override
    public UserDomain createUser(UserDomain userDomain){
        userRepository.save(userMapper.toUserEntity(userDomain));
        return userDomain;
    }
}
