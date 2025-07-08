package com.example.gestionAcueducto.dto.users;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfoRequestDTO {

	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String address;
	private String phoneNumber;
}
