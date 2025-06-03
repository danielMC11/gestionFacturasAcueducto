package com.example.gestionAcueducto.service;


import com.example.gestionAcueducto.dto.UserInfoDTO;
import com.example.gestionAcueducto.entity.User;
import com.example.gestionAcueducto.enums.UserRole;
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
	public void createUser(UserInfoDTO userInfoDTO){
			userRepository.save(
				User.builder()
					.firstName(userInfoDTO.getName())
					.lastName(userInfoDTO.getLastName())
					.email(userInfoDTO.getEmail())
					.address(userInfoDTO.getAddress())
					.phoneNumber(userInfoDTO.getPhoneNumber())
					.role(UserRole.PERSON)
					.password(passwordEncoder.encode(userInfoDTO.getPassword()))
					.build()
			);
	}

	@Override
	public User findByEmail(String email){
		return userRepository.findByEmail(email).orElse(null);
	}

	@Override
	public void updatePassword(String newPassword, Long userId){
		userRepository.updatePassword(passwordEncoder.encode(newPassword), userId);
	}


}
