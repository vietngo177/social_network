package com.ghtk.social_network.application.controller;

import com.ghtk.social_network.application.request.ChangePasswordRequest;
import com.ghtk.social_network.exception.customexception.IdInvalidException;
import com.ghtk.social_network.exception.customexception.PasswordException;
import com.ghtk.social_network.domain.port.api.UserServicePort;
import com.ghtk.social_network.util.SecurityUtil;
import jakarta.mail.SendFailedException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserServicePort  userServicePort;
    public UserController(UserServicePort userServicePort) {
        this.userServicePort = userServicePort;
    }

    @PostMapping("/change_password")
    public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest) throws IdInvalidException, PasswordException, SendFailedException {
        String email = SecurityUtil.getCurrentUserLogin().isPresent() ?
                SecurityUtil.getCurrentUserLogin().get() : "";

        if(email.isEmpty()){
            throw new IdInvalidException("Access token khong hop le");
        }

        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getRepeatPassword())) {
            throw new PasswordException("The new password does not match the repeat password");
        }

        String result = userServicePort.changePassword(email, changePasswordRequest);

        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/delete_account")
    public ResponseEntity<String> deleteAccount() throws IdInvalidException, PasswordException, SendFailedException {
        String email = SecurityUtil.getCurrentUserLogin().isPresent() ?
                SecurityUtil.getCurrentUserLogin().get() : "";

        if(email.isEmpty()){
            throw new IdInvalidException("Access token invalided");
        }

        String result = userServicePort.deleteAccount(email);

        return ResponseEntity.ok().body(result);
    }

}
