package com.ghtk.social_network.config;

import com.ghtk.social_network.domain.model.UserDomain;
import com.ghtk.social_network.domain.port.api.UserServicePort;
import com.ghtk.social_network.domain.port.spi.UserDatabasePort;
import com.ghtk.social_network.domain.service.UserServicePortImpl;
import com.ghtk.social_network.infrastracture.adapter.UserDatabaseAdapter;
import com.ghtk.social_network.infrastracture.repository.UserRepository;
import com.ghtk.social_network.util.mapper.UserMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    public UserServicePort userServicePort(UserDatabasePort userDatabasePort){
        return new UserServicePortImpl(userDatabasePort);
    }
    @Bean
    public UserDatabasePort userDatabasePort(UserRepository userRepository, UserMapper userMapper){
        return new UserDatabaseAdapter(userRepository, userMapper);
    }
}
