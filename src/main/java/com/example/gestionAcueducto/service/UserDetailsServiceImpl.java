package com.example.gestionAcueducto.service;

import com.example.gestionAcueducto.entity.UserInfo;
import com.example.gestionAcueducto.repository.UserInfoRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	private UserInfoRepository userInfoRepository;
	public UserDetailsServiceImpl(UserInfoRepository userInfoRepository){
		this.userInfoRepository = userInfoRepository;
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserInfo> usuario = userInfoRepository.findByEmail(username);
		if(usuario.isEmpty()) {
			throw new UsernameNotFoundException("Usuario o password inválidos");
		}

		return User.builder()
			.username(usuario.get().getEmail())
			.password(usuario.get().getPassword())
			.roles(
				String.valueOf(List.of(new SimpleGrantedAuthority(
					usuario.get().getRole().name())))
			).build();
	}
}
