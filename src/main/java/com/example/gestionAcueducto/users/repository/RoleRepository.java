package com.example.gestionAcueducto.users.repository;

import com.example.gestionAcueducto.users.enums.RoleName;
import com.example.gestionAcueducto.users.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleName(RoleName roleName);
}
