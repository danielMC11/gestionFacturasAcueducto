package com.example.gestionAcueducto.service;


import com.example.gestionAcueducto.dto.users.UserInfoRequestDTO;
import com.example.gestionAcueducto.entity.User;
import com.example.gestionAcueducto.enums.UserRole;
import com.example.gestionAcueducto.exceptions.domain.NotFoundException;
import com.example.gestionAcueducto.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;


	@Override
	public void createUser(UserInfoRequestDTO userInfoRequestDTO){
			userRepository.save(
				User.builder()
					.firstName(userInfoRequestDTO.getFirstName())
					.lastName(userInfoRequestDTO.getLastName())
					.email(userInfoRequestDTO.getEmail())
					.address(userInfoRequestDTO.getAddress())
					.phoneNumber(userInfoRequestDTO.getPhoneNumber())
					.role(UserRole.PERSON)
					.password(passwordEncoder.encode(userInfoRequestDTO.getPassword()))
					.build()
			);
	}

	@Override
	public User findByEmail(String email){
		return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("CORREO ELECTRÓNICO NO REGISTRADO"));
	}

	@Override
	public void updatePassword(String newPassword, Long userId){
		userRepository.updatePassword(passwordEncoder.encode(newPassword), userId);
	}


}
