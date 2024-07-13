package com.ghtk.social_network.config;

import com.ghtk.social_network.domain.port.api.UserServicePort;
import com.ghtk.social_network.domain.port.spi.UserDatabasePort;
import com.ghtk.social_network.domain.service.UserServicePortImpl;
import com.ghtk.social_network.infrastracture.adapter.UserDatabaseAdapter;
import com.ghtk.social_network.infrastracture.repository.RoleRepository;
import com.ghtk.social_network.infrastracture.repository.UserRepository;
import com.ghtk.social_network.util.mapper.UserMapper1;
import com.ghtk.social_network.util.MailService;
import com.ghtk.social_network.util.mapper.RoleMapper;
import com.ghtk.social_network.util.mapper.UserMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeanConfiguration {
    @Bean
    public UserServicePort userServicePort(UserDatabasePort userDatabasePort, MailService mailService, PasswordEncoder passwordEncoder){
        return new UserServicePortImpl(userDatabasePort,mailService, passwordEncoder);
    }
    @Bean
    public UserDatabasePort userDatabasePort(UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper, RoleMapper roleMapper, UserMapper1 userMapper1, PasswordEncoder passwordEncoder){
        return new UserDatabaseAdapter(userRepository,roleRepository,userMapper1, userMapper, passwordEncoder);
    }

    @Bean
    MailService domainMailService(JavaMailSender mailSender) {
        return new MailService(mailSender);
    }
}
