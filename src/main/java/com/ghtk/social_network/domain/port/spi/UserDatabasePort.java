package com.ghtk.social_network.domain.port.spi;


import com.ghtk.social_network.domain.model.User;
import com.ghtk.social_network.domain.model.UserDomain;

public interface UserDatabasePort {
    UserDomain findUserByEmail(String email);
    UserDomain createUser(UserDomain userDomain);
    UserDomain updateUserRefreshToken(UserDomain userDomain);

    UserDomain findUserByRefeshTokenAndEmail(String token, String email);
    void register(User user);

    User findByToken(int token);

    User findByEmail(String email);

    User findByUsername(String username);

    void updateRegisterUser(User user);

    void updateToken(User user);

    void updatePassword(User user);
}

