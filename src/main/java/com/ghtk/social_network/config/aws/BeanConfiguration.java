package com.ghtk.social_network.config.aws;

import com.ghtk.social_network.SocialNetworkApplication;
import com.ghtk.social_network.domain.port.api.UserServicePort;
import com.ghtk.social_network.domain.port.spi.UserDatabasePort;
import com.ghtk.social_network.domain.service.UserServicePortImpl;
import com.ghtk.social_network.infrastracture.adapter.UserDatabaseAdapter;
import com.ghtk.social_network.infrastracture.repository.RoleRepository;
import com.ghtk.social_network.util.MailService;
import com.ghtk.social_network.infrastracture.repository.UserRepository;
import com.ghtk.social_network.mapper.UserMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
@ComponentScan(basePackageClasses = SocialNetworkApplication.class)
public class BeanConfiguration {
    @Bean
    public UserServicePort userServicePort(UserDatabasePort userDatabasePort, MailService mailService) {
        return new UserServicePortImpl(userDatabasePort, mailService);
    }

    @Bean
    public UserDatabasePort userDatabasePort(UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper) {
        return new UserDatabaseAdapter(userRepository, roleRepository, userMapper);
    }

    @Bean
    MailService domainMailService(JavaMailSender mailSender) {
        return new MailService(mailSender);
    }
}
