package com.ghtk.social_network.domain.port.spi;

import com.ghtk.social_network.domain.model.UserDomain;

public interface UserDatabasePort {
    UserDomain findUserByEmail(String email);
    UserDomain createUser(UserDomain userDomain);
}
