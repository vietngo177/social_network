package com.ghtk.social_network.application.controller;

import com.ghtk.social_network.application.request.ChangePasswordRequest;
import com.ghtk.social_network.exception.handler.IdInvalidException;
import com.ghtk.social_network.exception.handler.PasswordException;
import com.ghtk.social_network.domain.port.api.UserServicePort;
import com.ghtk.social_network.util.SecurityUtil;
import jakarta.mail.SendFailedException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/v1")
public class UserController {
    private final UserServicePort  userServicePort;
    public UserController(UserServicePort userServicePort) {
        this.userServicePort = userServicePort;
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
