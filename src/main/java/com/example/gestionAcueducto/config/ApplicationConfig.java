package com.example.gestionAcueducto.config;

import com.example.gestionAcueducto.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepository userRepository;


    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ObjectMapper objectMapper(){

        ObjectMapper objectMapper = new ObjectMapper();
        // ===> REGISTRAR EL MÓDULO PARA FECHAS DE JAVA 8 <===
        objectMapper.registerModule(new JavaTimeModule());

        // Opcional: Para que las fechas se serialicen como cadenas ISO 8601 (2025-06-13T19:20:44),
        // en lugar de como marcas de tiempo numéricas (timestamps)
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // Puedes añadir más configuraciones aquí
        // objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        return objectMapper;}

}
