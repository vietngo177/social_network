package com.ghtk.social_network;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.ghtk.social_network.application", "com.ghtk.social_network.util", "com.ghtk.social_network.config","com.ghtk.social_network.infrastracture", "com.ghtk.social_network.domain.port.api"})
public class SocialNetworkApplication {
	public static void main(String[] args) {
		SpringApplication.run(SocialNetworkApplication.class, args);
	}
}
