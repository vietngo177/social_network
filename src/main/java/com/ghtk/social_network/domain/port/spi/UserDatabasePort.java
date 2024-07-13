package com.ghtk.social_network.domain.port.spi;


import com.ghtk.social_network.domain.model.User;

public interface UserDatabasePort {
    User findUserByEmail(String email);
    User createUser(User User);
    User updateUserRefreshToken(User User);

    User findUserByRefeshTokenAndEmail(String token, String email);
    void register(User user);

    User findByToken(int token);

    User findByEmail(String email);

    User findByUsername(String username);

    void updateRegisterUser(User user);

    void updateToken(User user);

    void updatePassword(User user);
}

