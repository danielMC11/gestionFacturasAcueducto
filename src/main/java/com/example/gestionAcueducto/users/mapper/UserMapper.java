package com.example.gestionAcueducto.users.mapper;


import com.example.gestionAcueducto.users.dto.UserDTO;
import com.example.gestionAcueducto.users.entity.User;
import com.example.gestionAcueducto.users.repository.projections.UserProjection;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO entityToDto(User user);

    UserDTO projectionToDto(UserProjection userProjection);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UserDTO userDTO, @MappingTarget User updatedUser);

}
