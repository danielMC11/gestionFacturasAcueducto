package com.example.gestionAcueducto.mapper;


import com.example.gestionAcueducto.dto.users.UserInfoRequestDTO;
import com.example.gestionAcueducto.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserInfoRequestDTO toDTO(User user);

    @Named("toDtoIgnorePassword")
    @Mapping(target = "password", ignore = true)
    UserInfoRequestDTO toDtoIgnorePassword(User user);
}
