package com.ghtk.social_network.domain.port.api;


import com.ghtk.social_network.application.request.ChangePasswordRequest;
import com.ghtk.social_network.exception.handler.PasswordException;
import com.ghtk.social_network.domain.model.UserDomain;
import jakarta.mail.SendFailedException;

public interface UserServicePort {
    UserDomain findUserByEmail(String email);
    UserDomain createUser(UserDomain userDomain);
    void updateRefreshToken(String token, String email);
    UserDomain findUserByRefreshTokenAndEmail(String token, String email);

    String changePassword(String email, ChangePasswordRequest changePasswordRequest) throws SendFailedException, PasswordException;

    String deleteAccount(String email) throws SendFailedException;
}
