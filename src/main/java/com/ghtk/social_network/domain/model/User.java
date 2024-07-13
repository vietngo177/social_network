package com.ghtk.social_network.domain.model;

import com.ghtk.social_network.util.constant.Gender;
import com.ghtk.social_network.util.constant.Role;
import com.ghtk.social_network.util.constant.Visibility;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    String username;
    String password;
    String email;
    String firstName;
    String lastName;
    Gender gender;
    LocalDate dateOfBirth;
    Role role;
    String location;
    String work;
    String education;
    String avatar;
    String backgroundImage;
    String refreshToken;
    Visibility visibility;
    int token;
    boolean enabled;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
