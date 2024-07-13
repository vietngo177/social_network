package com.ghtk.social_network.infrastracture.entity;

import com.ghtk.social_network.domain.model.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "roles")
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Data

public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", nullable = false)
    Long roleId;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name", nullable = false)
    Role roleName;

    public RoleEntity(Role roleName) {
        this.roleName = roleName;
    }
}