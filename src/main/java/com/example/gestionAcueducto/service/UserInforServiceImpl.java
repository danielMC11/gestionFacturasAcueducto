package com.example.gestionAcueducto.service;


import com.example.gestionAcueducto.dto.UserInfoDTO;
import com.example.gestionAcueducto.entity.UserInfo;
import com.example.gestionAcueducto.enums.UserRole;
import com.example.gestionAcueducto.repository.UserInfoRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserInforServiceImpl implements UserInfoService {

	private UserInfoRepository userInfoRepository;
	private PasswordEncoder passwordEncoder;

	public UserInforServiceImpl(UserInfoRepository userInfoRepository, PasswordEncoder passwordEncoder) {
		this.userInfoRepository = userInfoRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void createUser(UserInfoDTO userInfoDTO){
			userInfoRepository.save(
				UserInfo.builder()
					.name(userInfoDTO.getName())
					.lastname(userInfoDTO.getLastName())
					.email(userInfoDTO.getEmail())
					.address(userInfoDTO.getAddress())
					.phoneNumber(userInfoDTO.getPhoneNumber())
					.role(UserRole.ADMIN)
					.password(passwordEncoder.encode(userInfoDTO.getPassword()))
					.build()
			);
	}

}
