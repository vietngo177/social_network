package com.ghtk.social_network.infrastracture.entity;

import com.ghtk.social_network.domain.model.Gender;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long userId;
    String username;
    String password;
    String email;
    String firstName;
    String lastName;
    @Enumerated(EnumType.STRING)
    Gender gender;
    LocalDate dateOfBirth;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="role_id")
    RoleEntity role;
    String location;
    String work;
    String education;
    String avatar;
    String backgroundImage;
    int token;
    boolean enabled;
}
