package com.ghtk.social_network.domain.port.spi;

import com.ghtk.social_network.domain.model.User;

public interface UserDatabasePort {
    void register(User user);

    User findByToken(int token);

    User findByEmail(String email);

    User findByUsername(String username);

    void updateRegisterUser(User user);
}

