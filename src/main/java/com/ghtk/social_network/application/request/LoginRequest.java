package com.ghtk.social_network.application.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotBlank(message = "email khong duoc de trong")
    private String email;

    @NotBlank(message = "password khong duoc de trong")
    private String password;
}
