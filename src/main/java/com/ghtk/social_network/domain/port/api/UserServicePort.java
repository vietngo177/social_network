package com.ghtk.social_network.domain.port.api;

import com.ghtk.social_network.domain.model.UserDomain;
import org.springframework.stereotype.Component;

public interface UserServicePort {
    UserDomain findUserByEmail(String email);
    UserDomain createUser(UserDomain userDomain);
}
