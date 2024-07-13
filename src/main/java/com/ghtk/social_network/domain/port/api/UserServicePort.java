package com.ghtk.social_network.domain.port.api;

import com.ghtk.social_network.domain.model.User;
import jakarta.mail.MessagingException;
import com.ghtk.social_network.application.request.ChangePasswordRequest;
import com.ghtk.social_network.exception.handler.PasswordException;
import com.ghtk.social_network.domain.model.UserDomain;
import jakarta.mail.SendFailedException;

public interface UserServicePort {
    User findUserByEmail(String email);

    void updateRefreshToken(String token, String email);

    User findUserByRefreshTokenAndEmail(String token, String email);

    void register(String url, User user) throws MessagingException;

    String changePassword(String email, ChangePasswordRequest changePasswordRequest)
            throws SendFailedException, PasswordException;

    String forgotPassword(String url, String email) throws MessagingException;

    void confirmForgotPasswordToken(int token);

    String createNewPassword(String password, int token);

    String deleteAccount(String email) throws SendFailedException;
}
