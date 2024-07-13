package com.ghtk.social_network.domain.service;

import com.ghtk.social_network.domain.model.Role;
import com.ghtk.social_network.domain.model.User;
import com.ghtk.social_network.domain.model.UserDomain;
import com.ghtk.social_network.domain.port.api.UserServicePort;
import com.ghtk.social_network.domain.port.spi.UserDatabasePort;
import com.ghtk.social_network.util.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.SendFailedException;

import java.util.Random;

public class UserServicePortImpl implements UserServicePort {
    final private UserDatabasePort userDatabasePort;
    final private MailService mailService;

    public UserServicePortImpl(UserDatabasePort userDatabasePort, MailService mailService) {
        this.userDatabasePort = userDatabasePort;
        this.mailService = mailService;
    }

    @Override
    public void register(String url, User user) throws MessagingException {
        if(userDatabasePort.findByEmail(user.getEmail()) != null) throw new RuntimeException("Email was registered! Please register by other email");
        if(userDatabasePort.findByUsername(user.getUsername()) != null) throw new RuntimeException("Username was registered! Please register by other username");
        Random rand = new Random();
        int token = rand.nextInt();
        String link = url + "/api/v1/register/confirm_register/" + token;
        mailService.sendMailRegister(user.getEmail(), link);
        user.setEnabled(false);
        user.setToken(token);
        user.setRole(Role.USER);
        userDatabasePort.register(user);
    }

    @Override
    public String confirmRegister(int token) {
        User user = userDatabasePort.findByToken(token);
        if (user == null) throw new RuntimeException("User did not register successfully") ;
        user.setEnabled(true);
        userDatabasePort.updateRegisterUser(user);
        return "User successfully registered";
    }

    @Override
    public String forgotPassword(String url, String email) throws MessagingException {
        Random rand = new Random();
        int token = rand.nextInt();
        User user = userDatabasePort.findByEmail(email);
        if (user == null) throw new SendFailedException("Invalid email or email does not exist");
        String link = url + "/api/v1/forgot_password/confirm_password/" + token;
        user.setToken(token);
        userDatabasePort.register(user);
        mailService.sendMailRegister(email, link);
        return "Please check your email to change your password.";
    }

    @Override
    public String confirmPassword(int token, String password) {
        User user = userDatabasePort.findByToken(token);
        if (user == null) throw new RuntimeException("Error happened");
        user.setPassword(password);
        userDatabasePort.register(user);
        return "User successfully change password";
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
