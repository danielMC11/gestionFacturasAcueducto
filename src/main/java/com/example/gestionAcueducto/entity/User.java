package com.example.gestionAcueducto.entity;

import com.example.gestionAcueducto.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	@Column(length = 30)
	private String firstName;

	@Column(length = 30)
	private String lastName;

	@Column(nullable = false, unique = true, length = 254)
	private String email;

	@Column(nullable = true)
	private String password;

	@Column(nullable = false)
	private String address;

	@Column(length = 15)
	private String phoneNumber;

	@Enumerated(EnumType.STRING)
	private UserRole role;

}
