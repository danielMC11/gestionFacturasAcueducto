package com.altamira.users.service;

import com.altamira.users.enums.Role;
import com.altamira.users.dto.UserDTO;
import com.altamira.users.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

	UserDTO createUser(UserDTO userDTO, Role role);

	UserDTO updateUser(Long id, UserDTO userDTO);

	void deactivateSubscriber(Long id);

	User findByEmail(String email);

	void updatePassword(String newPassword, Long userId);

	Page<UserDTO> findAllSummary(Pageable pageable);

		UserDTO findById(Long id);


}
