package com.example.gestionAcueducto.service;

import com.example.gestionAcueducto.entity.PasswordResetToken;
import com.example.gestionAcueducto.entity.User;

import com.example.gestionAcueducto.repository.PasswordResetTokenRepository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Service
public class PasswordResetTokenService {
	private PasswordResetTokenRepository passwordResetTokenRepository;

	public String createTokenForUser(User user){

			PasswordResetToken passwordResetToken = passwordResetTokenRepository.save(
				PasswordResetToken.builder()
					.user(user)
					.token(UUID.randomUUID().toString())
					.expirationDate(LocalDateTime.now().plusMinutes(5))
					.build()
			);
			return passwordResetToken.getToken();
	}

	public PasswordResetToken findByToken(String token){
		return passwordResetTokenRepository.findByToken(token).orElse(null);
	}

	public boolean isTokenExpired(PasswordResetToken passwordResetToken){
		return passwordResetToken.getExpirationDate().isBefore(LocalDateTime.now());
	}


	public void deleteToken(PasswordResetToken passwordResetToken){
		passwordResetTokenRepository.delete(passwordResetToken);
	}


}