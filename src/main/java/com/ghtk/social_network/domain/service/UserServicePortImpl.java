package com.ghtk.social_network.domain.service;

import com.ghtk.social_network.util.constant.Role;
import com.ghtk.social_network.domain.model.User;
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
        String link = url + "/api/v1/auth/register/confirm_register/" + token;
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
        String link = url + "/api/v1/auth/forgot_password/confirm_request/" + token;
        user.setToken(token);
        userDatabasePort.updateToken(user);
        mailService.sendMailForgetPassword(email, link);
        return "Please check your email to change your password.";
    }

    @Override
    public void confirmForgotPasswordToken(int token) {
        User user = userDatabasePort.findByToken(token);
        if (user == null) throw new RuntimeException("The path to retrieve your password is no longer available") ;
    }

    @Override
    public String createNewPassword(String password, int token) {
        User user = userDatabasePort.findByToken(token);
        user.setPassword(password);
        userDatabasePort.updatePassword(user);
        return "User changed password successfully";
    }

    @Override
    public User findUserByEmail(String email) {
        return userDatabasePort.findByEmail(email);
    }

    @Override
    public void updateRefreshToken(String token, String email) {
        User currentUser = this.userDatabasePort.findByEmail(email);
        if(currentUser != null){
            currentUser.setRefreshToken(token);
            this.userDatabasePort.updateUserRefreshToken(currentUser);
        }
    }

    @Override
    public User findUserByRefreshTokenAndEmail(String token, String email) {
        return this.userDatabasePort.findUserByRefeshTokenAndEmail(token, email);
    }

}
