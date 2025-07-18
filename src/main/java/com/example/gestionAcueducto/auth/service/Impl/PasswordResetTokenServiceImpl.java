package com.example.gestionAcueducto.auth.service.Impl;

import com.example.gestionAcueducto.auth.entity.PasswordResetToken;
import com.example.gestionAcueducto.users.entity.User;

import com.example.gestionAcueducto.auth.service.PasswordResetTokenService;
import com.example.gestionAcueducto.exceptions.domain.NotFoundException;
import com.example.gestionAcueducto.auth.repository.PasswordResetTokenRepository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {
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
		return passwordResetTokenRepository.findByToken(token).orElseThrow(() -> new NotFoundException("TOKEN NO REGISTRADO"));
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
