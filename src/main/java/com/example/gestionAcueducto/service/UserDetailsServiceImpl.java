package com.example.gestionAcueducto.service;

import com.example.gestionAcueducto.entity.User;
import com.example.gestionAcueducto.enums.UserRole;
import com.example.gestionAcueducto.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	private UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> usuario = userRepository.findByEmail(username);
		if(usuario.isEmpty()) {
			throw new UsernameNotFoundException("Usuario o password inválidos");
		}

		return org.springframework.security.core.userdetails.User.builder()
			.username(usuario.get().getEmail())
			.password(usuario.get().getPassword())
			.roles(
				String.valueOf(List.of(new SimpleGrantedAuthority(
					usuario.get().getRole().name())))
			).build();
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<UserRole> roles){
		return roles.stream()
			.map(role -> new SimpleGrantedAuthority(role.name()))
			.collect(Collectors.toList());
	}
}
