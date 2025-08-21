package com.example.gestionAcueducto.users.service;

import com.example.gestionAcueducto.users.enums.RoleName;
import com.example.gestionAcueducto.users.dto.UserDTO;
import com.example.gestionAcueducto.users.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

	UserDTO createUser(UserDTO userDTO, RoleName roleName);

	UserDTO updateUser(Long id, UserDTO userDTO);

	void deleteUser(Long id);

	User findById(Long id);

	User findByEmail(String email);

	void updatePassword(String newPassword, Long userId);

	Page<UserDTO> findAll(Pageable pageable);

}
