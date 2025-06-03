package com.example.gestionAcueducto.enums;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public enum UserRole {
	ADMIN,
	PERSON;

	public List<SimpleGrantedAuthority> getAuthorities() {
		return  new ArrayList<SimpleGrantedAuthority>(
                List.of(new SimpleGrantedAuthority("ROLE_" + this.name()))
		);
	}
}
