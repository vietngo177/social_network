package com.ghtk.social_network.infrastracture.entity;

import com.ghtk.social_network.util.constant.Gender;
import com.ghtk.social_network.util.constant.Visibility;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "date_of_birth")
    LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    Gender gender;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "visibility")
    private Visibility visibility;

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "role_id")
    private RoleEntity roleEntity;

    @Column(name = "bio")
    private String bio;

    @Column(name = "location")
    private String location;

    @Column(name = "work")
    private String work;

    @Column(name = "education")
    private String education;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "background_image")
    private String backgroundImage;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "token")
    private Integer token;
    @Column(name = "is_deleted")
    boolean isDeleted;

    int token;
    boolean enabled;

    @Column(name = "enable")
    private boolean enable;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}