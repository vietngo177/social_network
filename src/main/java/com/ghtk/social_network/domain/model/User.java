package com.ghtk.social_network.domain.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

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
    int token;
    boolean enabled;
}
