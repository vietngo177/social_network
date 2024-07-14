package com.ghtk.social_network.application.request;

import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@RequestMapping
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterRequest {
    @NotBlank(message = "Username must not be empty")
    @Size(min = 6, message = "Username must contain 6 character at least")
    String username;

    @Size(min = 8, message = "Password must contain 8 character at least")
    @NotBlank(message = "Password must not be empty")
    String password;

    @Email
    @NotBlank(message = "Email must not be empty")
    String email;

    @NotBlank(message = "Firstname must not be empty")
    String firstName;

    @NotBlank(message = "Lastname must not be empty")
    String lastName;

    @NotBlank(message = "Gender must not be empty")
    String gender;

    @NotNull(message = "Date of birth must not be empty")
    LocalDate dateOfBirth;
}
