package com.example.gestionAcueducto.controller;

import com.example.gestionAcueducto.dto.UserInfoDTO;
import com.example.gestionAcueducto.entity.UserInfo;
import com.example.gestionAcueducto.service.UserInfoService;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthRestController {

	private UserInfoService userInfoService;

	public AuthRestController(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}

	@PostMapping("/hpta")
	public String hpta(){
		return "HPTA";
	}
	@PostMapping("/register")
	public String crear(@RequestBody UserInfoDTO userInfoDTO){
		try {
			userInfoService.createUser(userInfoDTO);
			return "Bien";
		}catch (Exception e){
			e.printStackTrace();
			return "mal";
		}
	}



}
