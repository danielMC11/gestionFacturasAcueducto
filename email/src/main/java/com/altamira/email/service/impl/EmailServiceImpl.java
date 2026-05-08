package com.altamira.email.service.impl;


import com.altamira.email.messaging.publishers.EmailPublisher;
import com.altamira.email.service.EmailService;
import com.altamira.common.events.email.PasswordUpdateEmailFailedEvent;
import com.altamira.common.events.email.PasswordUpdateEmailSentEvent;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.context.Context;
import org.thymeleaf.TemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@AllArgsConstructor
@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

	private final JavaMailSender emailSender;

	private final TemplateEngine templateEngine;

	private final EmailPublisher emailPublisher;


	public void sendResetPasswordEmail(UUID sagaId, String email, String templateName, String token){
		try {
			MimeMessage message = emailSender.createMimeMessage();

			MimeMessageHelper helper = new MimeMessageHelper(message,
				MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
				StandardCharsets.UTF_8.name());

			Map<String, Object> model = new HashMap<>();
			model.put("resetUrl", "http://localhost:5173/reset-password?token=" + token);

			Context context = new Context();
			context.setVariables(model);
			String html = templateEngine.process("email/" + templateName, context);

			helper.setFrom("danycmontero@gmail.com");
			helper.setTo(email);
			helper.setSubject("RESET PASSWORD");
			helper.setText(html, true);

			emailSender.send(message);

			log.info("Email Sent Successfully to {}.", email);

			emailPublisher.publishPasswordUpdateEmailSentEvent(new PasswordUpdateEmailSentEvent(sagaId));

		} catch (Exception e){

			log.error("Error sending email to {}. Error: {}", email, e.getMessage());

			emailPublisher.publishPasswordUpdateEmailFailedEvent(new PasswordUpdateEmailFailedEvent(sagaId, e.getMessage()));
		}
	}





}
