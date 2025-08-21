package com.example.gestionAcueducto.users.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE users SET active = false WHERE id = ?")
@SQLRestriction("active = true")
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
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

	@OneToOne
	@JoinColumn(name = "role_id")
	private Role role;

	private boolean active = Boolean.TRUE;

	@CreatedDate
	private LocalDateTime createdDate;

	@LastModifiedDate
	private LocalDateTime lastModifiedDate;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return  new ArrayList<SimpleGrantedAuthority>(
				List.of(new SimpleGrantedAuthority("ROLE_" + role.getRoleName().name()))
		);

	}

	@Override
	public String getUsername() {
		return email;
	}
}
