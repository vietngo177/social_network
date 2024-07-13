package com.ghtk.social_network.infrastracture.repository;

import com.ghtk.social_network.infrastracture.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByRefreshTokenAndEmail(String token, String email);

    UserEntity findByToken(int token);

    UserEntity findByEmail(String email);

    UserEntity findByUsername(String username);
}
