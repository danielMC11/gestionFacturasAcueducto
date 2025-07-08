package com.example.gestionAcueducto.service;

import com.example.gestionAcueducto.dto.users.UserInfoRequestDTO;
import com.example.gestionAcueducto.entity.User;

public interface UserService {
	void createUser(UserInfoRequestDTO userInfoRequestDTO);
	User findByEmail(String email);

	void updatePassword(String newPassword, Long userId);
}
