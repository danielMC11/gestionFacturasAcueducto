package com.example.gestionAcueducto.security;

import com.example.gestionAcueducto.security.jwt.AuthEntryPointJwt;
import com.example.gestionAcueducto.security.jwt.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import static com.example.gestionAcueducto.enums.UserRole.ADMIN;


@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

	private final String[] WHITE_LIST_URL = {"/auth/login", "/auth/register"};
	private final AuthEntryPointJwt unauthorizedHandler;
	private final AuthenticationProvider authenticationProvider;
	private final JwtTokenFilter jwtTokenFilter;


	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
				.exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(request -> {
					request.requestMatchers(WHITE_LIST_URL).permitAll();
					request.requestMatchers("/auth/data").hasRole(ADMIN.name());
					request.anyRequest().authenticated();
				})
				.authenticationProvider(authenticationProvider)
				.addFilterAfter(jwtTokenFilter, LogoutFilter.class);
		return http.build();
	}


}
