package com.example.gestionAcueducto.auth.repository;

import com.example.gestionAcueducto.auth.entity.RefreshToken;
import com.example.gestionAcueducto.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> deleteByUser(User user);
    Optional<RefreshToken> findByUser(User user);
}