package com.altamira.users.service.Impl;

import com.altamira.users.events.users.PasswordUpdateRequestedEvent;
import com.altamira.users.entity.PasswordResetToken;
import com.altamira.users.entity.User;

import com.altamira.users.enums.EmailStatus;
import com.altamira.users.messaging.publishers.PasswordResetPublisher;
import com.altamira.users.service.PasswordResetTokenService;
import com.altamira.users.repository.PasswordResetTokenRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {
	private final PasswordResetTokenRepository passwordResetTokenRepository;
	private final PasswordResetPublisher passwordResetPublisher;

	public String createPasswordResetToken(User user, int minutes) {

			UUID sagaId = UUID.randomUUID();

			PasswordResetToken passwordResetToken = passwordResetTokenRepository.save(
				PasswordResetToken.builder()
                        .user(user)
                        .token(UUID.randomUUID().toString())
                        .expirationDate(LocalDateTime.now().plusMinutes(minutes))
                        .sagaId(sagaId)
                        .emailStatus(EmailStatus.PENDING)
					.build()
			);


			PasswordUpdateRequestedEvent passwordUpdateRequestedEvent = new PasswordUpdateRequestedEvent( passwordResetToken.getSagaId(),
					user.getEmail(), "reset-password-email", passwordResetToken.getToken());

			passwordResetPublisher.publishPasswordUpdateRequestedEvent(passwordUpdateRequestedEvent);

			return sagaId.toString();

	}




	public void updatePasswordResetTokenStatus(UUID sagaId, EmailStatus emailStatus) {
		PasswordResetToken passwordResetToken = passwordResetTokenRepository.findBySagaId(sagaId).orElseThrow(
				() -> new RuntimeException("Password reset token not found")
		);

		passwordResetToken.setEmailStatus(emailStatus);

		passwordResetTokenRepository.save(passwordResetToken);
	}


	public PasswordResetToken findByToken(String token){
		return passwordResetTokenRepository.findByToken(token).orElseThrow(() -> new RuntimeException("TOKEN NO REGISTRADO"));
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
