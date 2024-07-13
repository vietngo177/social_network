package com.ghtk.social_network.infrastracture.repository;

import com.ghtk.social_network.domain.model.Role;
import com.ghtk.social_network.infrastracture.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
    RoleEntity findByRoleName(Role role );
}