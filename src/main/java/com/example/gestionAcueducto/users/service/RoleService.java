package com.example.gestionAcueducto.users.service;


import com.example.gestionAcueducto.users.enums.RoleName;
import com.example.gestionAcueducto.users.entity.Role;

public interface RoleService {
    Role findByRoleName(RoleName name);
}
