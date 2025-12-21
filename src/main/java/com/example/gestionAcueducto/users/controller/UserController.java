package com.example.gestionAcueducto.users.controller;


import com.example.gestionAcueducto.users.enums.RoleName;
import com.example.gestionAcueducto.users.service.UserService;
import com.example.gestionAcueducto.users.dto.UserDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("register-owner")
    public ResponseEntity<UserDTO> createOwner(@Valid @RequestBody UserDTO userDTO){
        return ResponseEntity.ok(userService.createUser(userDTO, RoleName.OWNER));
    }

    @PostMapping("register-admin")
    public ResponseEntity<UserDTO> createAdmin(@Valid @RequestBody UserDTO userDTO){
        return ResponseEntity.ok(userService.createUser(userDTO, RoleName.ADMIN));
    }

    @PostMapping("register-client")
    public ResponseEntity<UserDTO> createClient(@Valid @RequestBody UserDTO userDTO){
        return ResponseEntity.ok(userService.createUser(userDTO, RoleName.CLIENT));
    }


    @PutMapping("update/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO){
        return ResponseEntity.ok(userService.updateUser(id, userDTO));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("list-all")
    public PagedModel<UserDTO> findAll(@PageableDefault(page = 0, size = 20, sort = "lastName", direction = Sort.Direction.ASC) Pageable pageable){
        return new PagedModel<>(userService.findAll(pageable));
    }


}
