package com.ghtk.social_network.domain.service;

import com.ghtk.social_network.application.request.ChangePasswordRequest;
import com.ghtk.social_network.exception.handler.PasswordException;
import com.ghtk.social_network.domain.model.User;
import com.ghtk.social_network.domain.model.UserDomain;
import com.ghtk.social_network.domain.port.api.UserServicePort;
import com.ghtk.social_network.domain.port.spi.UserDatabasePort;
import com.ghtk.social_network.util.MailService;
import jakarta.mail.SendFailedException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserServicePortImpl implements UserServicePort {
    final private UserDatabasePort userDatabasePort;
    final private MailService mailService;

    final private PasswordEncoder passwordEncoder;

    public UserServicePortImpl(UserDatabasePort userDatabasePort, MailService mailService, PasswordEncoder passwordEncoder) {
        this.userDatabasePort = userDatabasePort;
        this.mailService = mailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String changePassword(String email, ChangePasswordRequest changePasswordRequest) throws SendFailedException, PasswordException {
        User user = userDatabasePort.findByEmail(email);
        if (user == null) throw new SendFailedException("Invalid email or email does not exist");

        if(passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())) {
            user.setPassword(changePasswordRequest.getNewPassword());
            userDatabasePort.updatePassword(user);
        } else {
            throw new PasswordException("Old password is incorrect");
        }

        return "Changed password successfully";
    }

    @Override
    public String deleteAccount(String email) throws SendFailedException {
        User user = userDatabasePort.findByEmail(email);
        if (user == null) throw new SendFailedException("Invalid email or email does not exist");
        userDatabasePort.deleteAccount(user);

        return "Deleted account successfully";
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
