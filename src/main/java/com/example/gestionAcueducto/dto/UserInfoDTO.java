package com.example.gestionAcueducto.dto;


import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.stereotype.Service;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDTO {

	private String name;
	private String lastName;
	private String email;
	private String password;
	private String address;
	private String phoneNumber;
}
