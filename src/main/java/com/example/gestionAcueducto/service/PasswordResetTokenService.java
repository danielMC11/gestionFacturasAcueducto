package com.example.gestionAcueducto.service;

import com.example.gestionAcueducto.entity.PasswordResetToken;
import com.example.gestionAcueducto.entity.User;

import com.example.gestionAcueducto.repository.PasswordResetTokenRepository;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
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


	public PasswordResetToken findByUser(User user){
		return passwordResetTokenRepository.findByUser(user).orElse(null);
	}

	public boolean isTokenExpired(PasswordResetToken passwordResetToken){
		if(passwordResetToken != null)
			return passwordResetToken.getExpirationDate().isBefore(LocalDateTime.now());
		return false;
	}


	public void deleteToken(PasswordResetToken passwordResetToken){
		passwordResetTokenRepository.delete(passwordResetToken);
	}


}
