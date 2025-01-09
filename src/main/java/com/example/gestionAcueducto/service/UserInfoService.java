package com.example.gestionAcueducto.service;

import com.example.gestionAcueducto.dto.UserInfoDTO;
import com.example.gestionAcueducto.entity.UserInfo;

public interface UserInfoService {
	void createUser(UserInfoDTO userInfoDTO);
}
