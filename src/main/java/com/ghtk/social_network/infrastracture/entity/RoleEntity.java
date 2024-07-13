package com.ghtk.social_network.infrastracture.entity;

import com.ghtk.social_network.domain.model.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "roles")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer roleId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    Role roleName;

    public RoleEntity(Role roleName) {
        this.roleName = roleName;
    }
}
