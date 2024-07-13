package com.ghtk.social_network.infrastracture.adapter;

import com.ghtk.social_network.util.constant.Role;
import com.ghtk.social_network.domain.model.User;
import com.ghtk.social_network.domain.port.spi.UserDatabasePort;
import com.ghtk.social_network.infrastracture.entity.RoleEntity;
import com.ghtk.social_network.infrastracture.entity.UserEntity;
import com.ghtk.social_network.infrastracture.repository.RoleRepository;
import com.ghtk.social_network.infrastracture.repository.UserRepository;
import com.ghtk.social_network.util.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class UserDatabaseAdapter implements UserDatabasePort {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void register(User user) {
        UserEntity userEntity = userMapper.toUserEntity(user);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        RoleEntity roleEntity = roleRepository.findByName("USER");
        userEntity.setRoleEntity(roleEntity);
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
        userEntity.setEnable(true);
        userEntity.setToken(0);
        userRepository.save(userEntity);
    }

    @Override
    public void updateToken(User user) {
        UserEntity userEntity = userRepository.findByEmail(user.getEmail());
        userEntity.setToken(user.getToken());
        userRepository.save(userEntity);
    }

    @Override
    public void updatePassword(User user) {
        UserEntity userEntity = userRepository.findByEmail(user.getEmail());
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setToken(0);
        userRepository.save(userEntity);
    }

    @Override
    public User findUserByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        return userMapper.toUser(userEntity);
    }

    @Override
    public User createUser(User User) {
        return null;
    }

    @Override
    public User updateUserRefreshToken(User User) {
        UserEntity userEntity = userRepository.findByEmail(User.getEmail());
        userEntity.setRefreshToken(User.getRefreshToken());

        UserEntity userEntity1 = userRepository.save(userEntity);

        return userMapper.toUser(userEntity);
    }

    @Override
    public User findUserByRefeshTokenAndEmail(String token, String email) {
        return userMapper.toUser(userRepository.findByRefreshTokenAndEmail(token, email));

    }
}
