package com.ghtk.social_network.domain.model;

import com.ghtk.social_network.util.constant.Visibility;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class UserDomain {
    String username;
    String email;
    String password;
    String firstName;
    String lastName;
    Visibility visibility;
    RoleDomain role;
    String bio;
    String location;
    String work;
    String education;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    String avatar;
    String backgroundImage;
    String refreshToken;
}
