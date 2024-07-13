package com.ghtk.social_network.application.controller;

import com.ghtk.social_network.application.request.ChangePasswordRequest;
import com.ghtk.social_network.application.request.LoginRequest;
import com.ghtk.social_network.application.request.RegisterRequest;
import com.ghtk.social_network.domain.exception.IdInvalidException;
import com.ghtk.social_network.domain.exception.PasswordException;
import com.ghtk.social_network.domain.port.api.UserServicePort;
import com.ghtk.social_network.util.SecurityUtil;
import com.ghtk.social_network.util.mapper.UserMapper1;
import jakarta.mail.MessagingException;
import jakarta.mail.SendFailedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    private final UserServicePort  userServicePort;
    private final UserMapper1 userMapper1;

    public static class Utility {
        public static String getSiteURL(HttpServletRequest request) {
            String siteURL = request.getRequestURL().toString();
            return siteURL.replace(request.getServletPath(), "");
        }
    }

    public UserController(UserServicePort userServicePort, UserMapper1 userMapper1) {
        this.userServicePort = userServicePort;
        this.userMapper1 = userMapper1;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterRequest user, HttpServletRequest request) throws MessagingException {
        userServicePort.register(Utility.getSiteURL(request), userMapper1.toUser(user));
        return ResponseEntity.ok().body("Please check your email to confirm your account.");
    }

    @GetMapping("/register/confirm_register/{token}")
    public ResponseEntity<String> confirmToken(@PathVariable int token) {
        return ResponseEntity.ok().body(userServicePort.confirmRegister(token));
    }

    @PostMapping("/forgot_password/{email}")
    public ResponseEntity<String> forgotPassword(HttpServletRequest request, @PathVariable String email) throws MessagingException {
        String result = userServicePort.forgotPassword(Utility.getSiteURL(request), email);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/forgot_password/confirm_password/{token}")
    public ResponseEntity<String> confirmPassword(@PathVariable int token, @RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok().body(userServicePort.confirmPassword(token, loginRequest.getPassword()));
    }

    @PostMapping("/users/change_password")
    public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest) throws IdInvalidException, PasswordException, SendFailedException {
        String email = SecurityUtil.getCurrentUserLogin().isPresent() ?
                SecurityUtil.getCurrentUserLogin().get() : "";

        if(email.equals("")){
            throw new IdInvalidException("Access token khong hop le");
        }

        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getRepeatPassword())) {
            throw new PasswordException("The new password does not match the repeat password");
        }

        String result = userServicePort.changePassword(email, changePasswordRequest);

        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/users/delete_account")
    public ResponseEntity<String> deleteAccount() throws IdInvalidException, PasswordException, SendFailedException {
        String email = SecurityUtil.getCurrentUserLogin().isPresent() ?
                SecurityUtil.getCurrentUserLogin().get() : "";

        if(email.equals("")){
            throw new IdInvalidException("Access token khong hop le");
        }

        String result = userServicePort.deleteAccount(email);

        return ResponseEntity.ok().body(result);
    }

}
