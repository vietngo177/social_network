package com.ghtk.social_network.application.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangePasswordRequest {

    @NotBlank(message = "The old password field is required")
    private String oldPassword;

    @NotBlank(message = "The new password field is required")
    @Size(min = 8, message = "Password must contain 8 characters at least")
    private String newPassword;

    @NotBlank(message = "The repeat password field is required")
    @Size(min = 8, message = "Password must contain 8 characters at least")
    private String repeatPassword;
}
