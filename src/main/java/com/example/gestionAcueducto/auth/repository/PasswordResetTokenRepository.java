package com.example.gestionAcueducto.auth.repository;

import com.example.gestionAcueducto.auth.entity.PasswordResetToken;
import com.example.gestionAcueducto.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

	Optional<PasswordResetToken> findByToken(String token);

	Optional<PasswordResetToken> findByUser(User user);

}
