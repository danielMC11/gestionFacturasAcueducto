package com.example.gestionAcueducto.auth.service.Impl;


import com.example.gestionAcueducto.auth.service.EmailService;
import com.example.gestionAcueducto.exceptions.domain.EmailSendException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.context.Context;
import org.thymeleaf.TemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


@AllArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {

	private final JavaMailSender emailSender;

	private final TemplateEngine templateEngine;


	public void sendResetPasswordEmail(String email, String templateName, String token){
		try {
			MimeMessage message = emailSender.createMimeMessage();

			MimeMessageHelper helper = new MimeMessageHelper(message,
				MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
				StandardCharsets.UTF_8.name());

			Map<String, Object> model = new HashMap<>();
			model.put("resetUrl", "http://localhost:8080/reset-password?token=" + token);

			Context context = new Context();
			context.setVariables(model);
			String html = templateEngine.process("email/" + templateName, context);

			helper.setFrom("danycmontero@gmail.com");
			helper.setTo(email);
			helper.setSubject("RESET PASSWORD");
			helper.setText(html, true);

			emailSender.send(message);
		} catch (Exception e){
			throw new EmailSendException("Error al enviar correo", e);
		}
	}





}
