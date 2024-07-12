package com.ghtk.social_network.domain.service;

import com.ghtk.social_network.domain.model.UserDomain;
import com.ghtk.social_network.domain.port.api.UserServicePort;
import com.ghtk.social_network.domain.port.spi.UserDatabasePort;

public class UserServicePortImpl implements UserServicePort {
    private final UserDatabasePort userDatabasePort;

    public UserServicePortImpl(UserDatabasePort userDatabasePort) {
        this.userDatabasePort = userDatabasePort;
    }

    @Override
    public UserDomain findUserByEmail(String email) {
        return userDatabasePort.findUserByEmail(email);
    }

    @Override
    public UserDomain createUser(UserDomain userDomain) {
        return userDatabasePort.createUser(userDomain);
    }
}
