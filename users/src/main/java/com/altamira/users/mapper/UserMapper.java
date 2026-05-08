package com.altamira.users.mapper;


import com.altamira.users.dto.UserDTO;
import com.altamira.users.entity.User;
import com.altamira.users.repository.projections.UserProjection;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {


    UserDTO entityToDto(User user);

    UserDTO projectionToDto(UserProjection userProjection);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    void updateEntityFromDto(UserDTO userDTO, @MappingTarget User updatedUser);

}
