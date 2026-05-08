package com.altamira.users.repository;

import com.altamira.users.entity.RefreshToken;
import com.altamira.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> deleteByUser(User user);
    Optional<RefreshToken> findByUser(User user);
}