package com.example.gestionAcueducto.users.controller;


import com.example.gestionAcueducto.users.enums.Role;
import com.example.gestionAcueducto.users.service.UserService;
import com.example.gestionAcueducto.users.dto.UserDTO;

import com.example.gestionAcueducto.validator.groups.OnCreate;
import com.example.gestionAcueducto.validator.groups.OnPatchSingleUpdate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@RestController
public class UserRestController {

    private final UserService userService;

    @PostMapping("register-owner")
    public ResponseEntity<UserDTO> createOwner(@Valid @RequestBody UserDTO userDTO){
        return ResponseEntity.ok(userService.createUser(userDTO, Role.OWNER));
    }

    @PostMapping("register-admin")
    public ResponseEntity<UserDTO> createAdmin(@Valid @RequestBody UserDTO userDTO){
        return ResponseEntity.ok(userService.createUser(userDTO, Role.ADMIN));
    }

    @PostMapping("/subscribers/create")
    public ResponseEntity<UserDTO> createSubscriber(@Validated(OnCreate.class) @RequestBody UserDTO userDTO){
        return ResponseEntity.ok(userService.createUser(userDTO, Role.SUBSCRIBER));
    }

    @PatchMapping("update/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Validated(OnPatchSingleUpdate.class) @RequestBody UserDTO userDTO){
        return ResponseEntity.ok(userService.updateUser(id, userDTO));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/subscribers/list-all")
    public PagedModel<UserDTO> findAllSummary(@PageableDefault(page = 0, size = 20, sort = "lastName", direction = Sort.Direction.ASC) Pageable pageable){
        return new PagedModel<>(userService.findAllSummary(pageable));
    }


}
