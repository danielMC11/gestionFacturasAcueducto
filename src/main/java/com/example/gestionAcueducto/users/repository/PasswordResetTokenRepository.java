package com.example.gestionAcueducto.users.repository;

import com.example.gestionAcueducto.users.entity.PasswordResetToken;
import com.example.gestionAcueducto.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

	Optional<PasswordResetToken> findByToken(String token);

	Optional<PasswordResetToken> findByUser(User user);

	Optional<PasswordResetToken> findBySagaId(UUID sagaId);
	
}
