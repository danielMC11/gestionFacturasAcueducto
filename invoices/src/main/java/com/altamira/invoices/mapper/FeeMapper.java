package com.altamira.invoices.mapper;

import com.altamira.invoices.dto.FeeDTO;
import com.altamira.invoices.entity.Fee;
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
