package com.ghtk.social_network.infrastracture.entity;

import com.ghtk.social_network.util.constant.Visibility;
import com.ghtk.social_network.domain.model.Gender;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    Long userId;

    @Column(name = "username", nullable = false, unique = true)
    String username;

    @Column(name = "email", nullable = false, unique = true)
    String email;

    @Column(name = "password", nullable = false)
    String password;

    @Column(name = "first_name", nullable = false)
    String firstName;

    @Column(name = "last_name", nullable = false)
    String lastName;

    @Enumerated(EnumType.STRING)
    Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "visibility")
    Visibility visibility;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    RoleEntity role;

    @Column(name = "bio")
    String bio;

    @Column(name = "location")
    String location;

    @Column(name = "work")
    String work;

    @Column(name = "education")
    String education;

    @Column(name = "created_at", nullable = false)
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @Column(name = "avatar")
    String avatar;

    @Column(name = "background_image")
    String backgroundImage;
    @Column(name = "date_of_birth")
    LocalDate dateOfBirth;

    @Column(columnDefinition = "MEDIUMTEXT")
    String refreshToken;

    @Column(name = "is_deleted")
    boolean isDeleted;

    int token;
    boolean enabled;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}


