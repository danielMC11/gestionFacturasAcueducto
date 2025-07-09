package com.example.gestionAcueducto.security;

import com.example.gestionAcueducto.exceptions.auth.CustomAccessDeniedHandler;
import com.example.gestionAcueducto.exceptions.auth.CustomUnauthorizedHandler;
import com.example.gestionAcueducto.security.jwt.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;


@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

	private final String[] WHITE_LIST_URL = {"/auth/login", "/auth/register", "/auth/test-refresh", "/forgot-password", "/reset-password"};
	private final CustomUnauthorizedHandler customUnauthorizedHandler;
	private final CustomAccessDeniedHandler customAccessDeniedHandler;
	private final AuthenticationProvider authenticationProvider;
	private final JwtTokenFilter jwtTokenFilter;


	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
				.exceptionHandling(exceptions -> exceptions
					.authenticationEntryPoint(customUnauthorizedHandler)
					.accessDeniedHandler(customAccessDeniedHandler)
				)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(request -> {
					request.requestMatchers(WHITE_LIST_URL).permitAll();
					//request.requestMatchers("/auth/data").hasRole(ADMIN.name());
					request.anyRequest().authenticated();
				})
				.addFilterAfter(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
				.authenticationProvider(authenticationProvider);
		return http.build();
	}


}
