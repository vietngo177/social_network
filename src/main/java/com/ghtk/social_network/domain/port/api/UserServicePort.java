package com.ghtk.social_network.domain.port.api;


import com.ghtk.social_network.domain.model.User;
import jakarta.mail.MessagingException;

public interface UserServicePort {
    User findUserByEmail(String email);

    void updateRefreshToken(String token, String email);

    User findUserByRefreshTokenAndEmail(String token, String email);

    void register(String url, User user) throws MessagingException;

    String confirmRegister(int token);

    String forgotPassword(String url, String email) throws MessagingException;

    void confirmForgotPasswordToken(int token);

    String createNewPassword(String password, int token);
}
