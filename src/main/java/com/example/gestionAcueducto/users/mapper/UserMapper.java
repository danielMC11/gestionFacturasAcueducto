package com.example.gestionAcueducto.users.mapper;


import com.example.gestionAcueducto.users.dto.UserDTO;
import com.example.gestionAcueducto.users.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {
    //@Mapping(target = "password", ignore = true)
    UserDTO entityToDto(User user);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UserDTO userDTO, @MappingTarget User updatedUser);

}
