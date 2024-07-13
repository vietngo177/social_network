package com.ghtk.social_network.infrastracture.adapter;

import com.ghtk.social_network.domain.model.Role;
import com.ghtk.social_network.domain.model.User;
import com.ghtk.social_network.domain.port.spi.UserDatabasePort;
import com.ghtk.social_network.infrastracture.entity.RoleEntity;
import com.ghtk.social_network.infrastracture.entity.UserEntity;
import com.ghtk.social_network.infrastracture.repository.RoleRepository;
import com.ghtk.social_network.infrastracture.repository.UserRepository;
import com.ghtk.social_network.mapper.UserMapper;

public class UserDatabaseAdapter implements UserDatabasePort {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    public UserDatabaseAdapter(UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
    }

    @Override
    public void register(User user) {
        UserEntity userEntity = userMapper.toUserEntity(user);
        RoleEntity role = roleRepository.findByRoleName(user.getRole());
        if(role == null) role = new RoleEntity(Role.USER);
        userEntity.setRole(role);
        userRepository.save(userEntity);
    }

    @Override
    public User findByToken(int token) {
        return userMapper.toUser(userRepository.findByToken(token));
    }

    @Override
    public User findByEmail(String email) {
        return userMapper.toUser(userRepository.findByEmail(email));
    }

    @Override
    public User findByUsername(String username) {
        return userMapper.toUser(userRepository.findByUsername(username));
    }

    @Override
    public void updateRegisterUser(User user) {
        UserEntity userEntity = userRepository.findByEmail(user.getEmail());
        userEntity.setEnabled(true);
        userEntity.setToken(0);
        userRepository.save(userEntity);
    }
}
