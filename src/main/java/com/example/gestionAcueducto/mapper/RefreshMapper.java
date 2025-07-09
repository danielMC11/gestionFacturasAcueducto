package com.example.gestionAcueducto.mapper;


import com.example.gestionAcueducto.dto.RefreshDTO;
import com.example.gestionAcueducto.entity.RefreshToken;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface RefreshMapper {

    @Mapping(target = "userInfoRequestDTO", source = "user", qualifiedByName = "toDtoIgnorePassword")
    RefreshDTO toDTO(RefreshToken refreshToken);
}
