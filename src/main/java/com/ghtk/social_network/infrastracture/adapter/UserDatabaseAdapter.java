package com.ghtk.social_network.infrastracture.adapter;

import com.ghtk.social_network.domain.model.Role;
import com.ghtk.social_network.domain.model.User;
import com.ghtk.social_network.domain.model.UserDomain;
import com.ghtk.social_network.domain.port.spi.UserDatabasePort;
import com.ghtk.social_network.infrastracture.entity.RoleEntity;
import com.ghtk.social_network.infrastracture.entity.UserEntity;
import com.ghtk.social_network.infrastracture.repository.RoleRepository;
import com.ghtk.social_network.infrastracture.repository.UserRepository;
import com.ghtk.social_network.util.mapper.UserMapper1;
import com.ghtk.social_network.util.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class UserDatabaseAdapter implements UserDatabasePort {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper1 userMapper1;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void register(User user) {
        UserEntity userEntity = userMapper1.toUserEntity(user);
        RoleEntity role = roleRepository.findByRoleName(user.getRole());
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        if(role == null) role = new RoleEntity(Role.USER);
        userEntity.setRole(role);
        userRepository.save(userEntity);
    }

    @Override
    public User findByToken(int token) {
        return userMapper1.toUser(userRepository.findByToken(token));
    }

    @Override
    public User findByEmail(String email) {
        return userMapper1.toUser(userRepository.findByEmail(email));
    }

    @Override
    public User findByUsername(String username) {
        return userMapper1.toUser(userRepository.findByUsername(username));
    }

    @Override
    public void updateRegisterUser(User user) {
        UserEntity userEntity = userRepository.findByEmail(user.getEmail());
        userEntity.setEnabled(true);
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
    public UserDomain findUserByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);

        return userMapper.toUserDomain(userEntity);
    }

    @Override
    public UserDomain createUser(UserDomain userDomain) {
        return null;
    }
//    @Override
//    public UserDomain createUser(UserDomain userDomain){
//        userRepository.save(userMapper.toUserEntity(userDomain));
//        return userDomain;
//    }

    @Override
    public UserDomain updateUserRefreshToken(UserDomain userDomain) {
        UserEntity userEntity = userRepository.findByEmail(userDomain.getEmail());
        userEntity.setRefreshToken(userDomain.getRefreshToken());

        UserEntity userEntity1 = userRepository.save(userEntity);

        return userMapper.toUserDomain(userEntity);
    }

    @Override
    public UserDomain findUserByRefeshTokenAndEmail(String token, String email) {
        return userMapper.toUserDomain(userRepository.findByRefreshTokenAndEmail(token, email));

    }
}
