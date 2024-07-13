package com.ghtk.social_network.config.aws;

import com.ghtk.social_network.infrastracture.repository.UserRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class JpaConfiguration {
}
