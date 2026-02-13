package com.example.gestionAcueducto.invoices.mapper;

import com.example.gestionAcueducto.invoices.dto.FeeDTO;
import com.example.gestionAcueducto.invoices.entity.Fee;
import com.example.gestionAcueducto.users.dto.UserDTO;
import com.example.gestionAcueducto.users.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface FeeMapper {

    FeeDTO entityToDto(Fee fee);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(FeeDTO feeDTO, @MappingTarget Fee updateFee);
}
