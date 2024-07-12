package com.ghtk.social_network.infrastracture.repository;

import com.ghtk.social_network.infrastracture.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String username);

}
