package com.ghtk.social_network.application.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank(message = "email khong duoc de trong")
    private String email;

    @NotBlank(message = "password khong duoc de trong")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String username) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
