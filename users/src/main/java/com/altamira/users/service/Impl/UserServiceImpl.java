package com.altamira.users.service.Impl;


import com.altamira.users.enums.Role;
import com.altamira.users.dto.UserDTO;
import com.altamira.users.entity.User;
import com.altamira.users.service.UserService;
import com.altamira.users.mapper.UserMapper;
import com.altamira.users.repository.UserRepository;
import com.altamira.users.util.PasswordGenerator;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	private PasswordGenerator passwordGenerator;
	private UserMapper userMapper;
	private final ApplicationEventPublisher eventPublisher;




	@Override
	public UserDTO createUser(UserDTO userDTO, Role role){

		userRepository.findByEmail(userDTO.email()).ifPresent(
				existingUser -> {
					throw new RuntimeException("Error al crear usuario: el usuario con el correo " +
							existingUser.getEmail() + " ya está en uso");
				}
				);

		User user =	userRepository.save(
				User.builder()
					.firstName(userDTO.firstName())
					.lastName(userDTO.lastName())
						.document(userDTO.document())
						.email(userDTO.email())
					.address(userDTO.address())
					.phoneNumber(userDTO.phoneNumber())
					.role(role)
					.password(passwordEncoder.encode(passwordGenerator.generateRandomPassword()))
					.build()
			);

		//eventPublisher.publishEvent(new UserCreatedEvent(this, user));

		return userMapper.entityToDto(user);
	}

	@Override
	public UserDTO updateUser(Long id, UserDTO userDTO) {
		User user = findUserById(id);

		userMapper.updateEntityFromDto(userDTO, user);

		User updatedUser = userRepository.save(user);

		return  userMapper.entityToDto(updatedUser);

	}

	@Override
	public void deactivateSubscriber(Long id) {
		userRepository.deleteById(id);
	}


	private User findUserById(Long id){
		return userRepository.findById(id).orElseThrow(()-> {
			String errorMessage = String.format("Error al obtener el usuario por ID: '%s'", id);
			return new RuntimeException(errorMessage);
		}
		);
	}

	@Override
	public User findByEmail(String email){
		return userRepository.findByEmail(email).orElseThrow(() -> {
			String errorMessage = String.format("El correo electrónico '%s' no está registrado", email);
			return new RuntimeException(errorMessage);
		});
	}

	@Transactional
	@Override
	public void updatePassword(String newPassword, Long userId){
		if (userRepository.updatePassword(passwordEncoder.encode(newPassword), userId) == 0) {
			throw new RuntimeException("Error al actualizar contraseña. El usuario no fue encontrado o la contraseña es la misma.");
		}
	}


	@Override
	public Page<UserDTO> findAllSummary(Pageable pageable) {

		return userRepository.findAllSummary(pageable)
				.map(userMapper::projectionToDto);
	}

	@Override
	public UserDTO findById(Long id) {
		return userMapper.entityToDto(findUserById(id));
	}
}
