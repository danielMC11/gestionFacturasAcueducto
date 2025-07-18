package com.example.gestionAcueducto.users.entity;

import com.example.gestionAcueducto.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Builder
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE users SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
@Table(name = "users")
@Entity
public class User implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 50, nullable = false)
	private String firstName;

	@Column(length = 50, nullable = false)
	private String lastName;

	@Column(length = 10, nullable = false, unique = true)
	private String document;


	@Column(nullable = false, unique = true, length = 254)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false, length = 254)
	private String address;

	@Column(nullable = false, length = 15)
	private String phoneNumber;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private UserRole role;

	private boolean deleted = Boolean.FALSE;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return role.getAuthorities();
	}

	@Override
	public String getUsername() {
		return email;
	}
}
