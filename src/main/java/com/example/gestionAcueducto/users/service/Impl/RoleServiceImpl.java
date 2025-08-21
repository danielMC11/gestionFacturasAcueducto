package com.example.gestionAcueducto.users.service.Impl;

import com.example.gestionAcueducto.users.enums.RoleName;
import com.example.gestionAcueducto.exceptions.domain.NotFoundException;
import com.example.gestionAcueducto.users.entity.Role;
import com.example.gestionAcueducto.users.repository.RoleRepository;
import com.example.gestionAcueducto.users.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    @Override
    public Role findByRoleName(RoleName roleName) {
        return roleRepository.findByRoleName(roleName).orElseThrow(()-> {
                    String errorMessage = String.format("Error al obtener el rol por nombre: '%s'", roleName);
                    return new NotFoundException(errorMessage);
                }
        );
    }
}
