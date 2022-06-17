package com.backend.backend.repository;

import com.backend.backend.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {

    @Query("FROM UserEntity WHERE email = :email")
    Optional<UserEntity> findUserByEmail(@Param("email") String email);

    boolean existsByEmail(String email);

}