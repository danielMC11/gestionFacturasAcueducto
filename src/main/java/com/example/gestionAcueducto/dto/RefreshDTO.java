package com.example.gestionAcueducto.dto;


import com.example.gestionAcueducto.dto.users.UserInfoRequestDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RefreshDTO {
    private Long id;
    private String token;
    private UserInfoRequestDTO userInfoRequestDTO;
    private Date expirationDate;
}
