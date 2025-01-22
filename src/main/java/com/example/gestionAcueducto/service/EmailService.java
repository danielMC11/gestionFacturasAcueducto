package com.example.gestionAcueducto.service;


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
public class EmailService {

	private JavaMailSender emailSender;

	private TemplateEngine templateEngine;


	public void sendResetPasswordEmail(String email, String token){
		try {
			MimeMessage message = emailSender.createMimeMessage();

			MimeMessageHelper helper = new MimeMessageHelper(message,
				MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
				StandardCharsets.UTF_8.name());

			Map<String, Object> model = new HashMap<>();
			model.put("resetUrl", "http://localhost:8080/reset-password?token=" + token);

			Context context = new Context();
			context.setVariables(model);
			String html = templateEngine.process("email/email-template", context);

			helper.setFrom("danielcarrillo3200@gmail.com");
			helper.setTo(email);
			helper.setSubject("RESET PASSWORD");
			helper.setText(html, true);

			emailSender.send(message);
		} catch (Exception e){
			throw new RuntimeException(e);
		}
	}


}
