package com.ghtk.social_network.domain.port.api;

import com.ghtk.social_network.domain.model.User;
import jakarta.mail.MessagingException;

public interface UserServicePort {
    void register(String url, User user) throws MessagingException;

    String confirmRegister(int token);

    String forgotPassword(String url, String email) throws MessagingException;

    String confirmPassword(int token, String password);
}
