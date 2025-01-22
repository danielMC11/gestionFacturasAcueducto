package com.example.gestionAcueducto.service;

import com.example.gestionAcueducto.dto.UserInfoDTO;
import com.example.gestionAcueducto.entity.User;

public interface UserService {
	void createUser(UserInfoDTO userInfoDTO);
	User findByEmail(String email);

	void updatePassword(String newPassword, Long userId);
}
