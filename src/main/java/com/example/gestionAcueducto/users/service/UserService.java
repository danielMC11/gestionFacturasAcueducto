package com.example.gestionAcueducto.users.service;

import com.example.gestionAcueducto.users.dto.UserDTO;
import com.example.gestionAcueducto.users.entity.User;

public interface UserService {
	UserDTO createUser(UserDTO userDTO);

	UserDTO updateUser(Long id, UserDTO userDTO);

	void deleteUser(Long id);

	User findById(Long id);

	User findByEmail(String email);

	void updatePassword(String newPassword, Long userId);
}
