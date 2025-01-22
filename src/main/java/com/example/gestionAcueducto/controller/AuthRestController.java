package com.example.gestionAcueducto.controller;

import com.example.gestionAcueducto.dto.UserInfoDTO;
import com.example.gestionAcueducto.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthRestController {

	private UserService userService;

	public AuthRestController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/register")
	public String crear(@RequestBody UserInfoDTO userInfoDTO){
		try {
			userService.createUser(userInfoDTO);
			return "Bien";
		}catch (Exception e){
			e.printStackTrace();
			return "mal";
		}
	}



}
