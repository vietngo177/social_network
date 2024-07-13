package com.ghtk.social_network.domain.port.api;


import com.ghtk.social_network.domain.model.User;
import com.ghtk.social_network.domain.model.UserDomain;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Component;

public interface UserServicePort {
    UserDomain findUserByEmail(String email);
    UserDomain createUser(UserDomain userDomain);
    void updateRefreshToken(String token, String email);
    UserDomain findUserByRefreshTokenAndEmail(String token, String email);
    void register(String url, User user) throws MessagingException;

    String confirmRegister(int token);

    String forgotPassword(String url, String email) throws MessagingException;

    String confirmPassword(int token, String password);
}
