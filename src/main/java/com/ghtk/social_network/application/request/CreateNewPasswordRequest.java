package com.ghtk.social_network.application.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateNewPasswordRequest {
    @Size(min = 8, message = "Password must contain 8 characters at least")
    @NotBlank(message = "Password must not empty")
    private String password;

    @Size(min = 8, message = "Password must contain 8 characters at least")
    @NotBlank(message = "Please confirm new password")
    private String confirmPassword;
}
