package com.example.gestionAcueducto.users.service.Impl;


import com.example.gestionAcueducto.users.dto.UserDTO;
import com.example.gestionAcueducto.users.entity.User;
import com.example.gestionAcueducto.users.service.UserService;
import com.example.gestionAcueducto.enums.UserRole;
import com.example.gestionAcueducto.exceptions.domain.DuplicateResourceException;
import com.example.gestionAcueducto.exceptions.domain.NotFoundException;
import com.example.gestionAcueducto.exceptions.domain.ResourceCreationException;
import com.example.gestionAcueducto.exceptions.domain.ResourceUpdateException;
import com.example.gestionAcueducto.users.mapper.UserMapper;
import com.example.gestionAcueducto.users.repository.UserRepository;
import com.example.gestionAcueducto.security.PasswordGenerator;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	private PasswordGenerator passwordGenerator;
	private UserMapper userMapper;


	@Override
	public UserDTO createUser(UserDTO userDTO){

		userRepository.findByEmail(userDTO.getEmail()).ifPresent(
				existingUser -> {
					throw new DuplicateResourceException("Error al crear usuario: el usuario con el correo " +
							existingUser.getEmail() + " ya está en uso");
				}
				);

		User user =	Optional.of(userRepository.save(
				User.builder()
					.firstName(userDTO.getFirstName())
					.lastName(userDTO.getLastName())
						.document(userDTO.getDocument())
						.email(userDTO.getEmail())
					.address(userDTO.getAddress())
					.phoneNumber(userDTO.getPhoneNumber())
					.role(UserRole.PERSON)
					.password(passwordEncoder.encode(passwordGenerator.generateRandomPassword()))
					.build()
			)).orElseThrow(()-> new ResourceCreationException("Error al crear nuevo usuario"));

		return userMapper.entityToDto(user);
	}

	@Override
	public UserDTO updateUser(Long id, UserDTO userDTO) {
		User user = findById(id);

		userMapper.updateEntityFromDto(userDTO, user);

		User updatedUser = Optional.of(userRepository.save(user))
				.orElseThrow(()-> new ResourceUpdateException("Error al actualizar usuario"));

		return  userMapper.entityToDto(updatedUser);

	}

	@Override
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	public User findById(Long id){
		return userRepository.findById(id).orElseThrow(()-> {
			String errorMessage = String.format("Error al obtener el usuario por ID: '%s'", id);
			return new NotFoundException(errorMessage);
		}
		);
	}

	@Override
	public User findByEmail(String email){
		return userRepository.findByEmail(email).orElseThrow(() -> {
			String errorMessage = String.format("El correo electrónico '%s' no está registrado", email);
			return new NotFoundException(errorMessage);
		});
	}

	@Transactional
	@Override
	public void updatePassword(String newPassword, Long userId){
		if (userRepository.updatePassword(passwordEncoder.encode(newPassword), userId) == 0) {
			throw new ResourceUpdateException("Error al actualizar contraseña. El usuario no fue encontrado o la contraseña es la misma.");
		}
	}


}
