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

    @Override
    public void updateRefreshToken(String token, String email) {
        UserDomain currentUser = this.userDatabasePort.findUserByEmail(email);
        if(currentUser != null){
            currentUser.setRefreshToken(token);
            this.userDatabasePort.updateUserRefreshToken(currentUser);
        }

    }

    @Override
    public UserDomain findUserByRefreshTokenAndEmail(String token, String email) {
        return this.userDatabasePort.findUserByRefeshTokenAndEmail(token, email);
    }
}
