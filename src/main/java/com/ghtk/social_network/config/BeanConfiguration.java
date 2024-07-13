package com.ghtk.social_network.config;

import com.ghtk.social_network.domain.port.api.UserServicePort;
import com.ghtk.social_network.domain.port.spi.UserDatabasePort;
import com.ghtk.social_network.domain.service.UserServicePortImpl;
import com.ghtk.social_network.infrastracture.adapter.UserDatabaseAdapter;
import com.ghtk.social_network.infrastracture.repository.RoleRepository;
import com.ghtk.social_network.infrastracture.repository.UserRepository;
import com.ghtk.social_network.util.mapper.UserMapper;
import com.ghtk.social_network.util.MailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeanConfiguration {
    @Bean
    public UserServicePort userServicePort(UserDatabasePort userDatabasePort, MailService mailService){
        return new UserServicePortImpl(userDatabasePort,mailService);
    }
    @Bean
    public UserDatabasePort userDatabasePort(UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper, PasswordEncoder passwordEncoder){
        return new UserDatabaseAdapter(userRepository,roleRepository, userMapper, passwordEncoder);
    }

    @Bean
    MailService domainMailService(JavaMailSender mailSender) {
        return new MailService(mailSender);
    }
}
