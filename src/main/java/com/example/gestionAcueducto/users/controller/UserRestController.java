package com.example.gestionAcueducto.users.controller;


import com.example.gestionAcueducto.users.enums.Role;
import com.example.gestionAcueducto.users.service.UserService;
import com.example.gestionAcueducto.users.dto.UserDTO;

import com.example.gestionAcueducto.validator.groups.OnCreate;
import com.example.gestionAcueducto.validator.groups.OnPutSingleUpdate;
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

    @PostMapping("owner/create")
    public ResponseEntity<UserDTO> createOwner(@Valid @RequestBody UserDTO userDTO){
        return ResponseEntity.ok(userService.createUser(userDTO, Role.OWNER));
    }

    @PostMapping("admin/create")
    public ResponseEntity<UserDTO> createAdmin(@Valid @RequestBody UserDTO userDTO){
        return ResponseEntity.ok(userService.createUser(userDTO, Role.ADMIN));
    }


    @PostMapping("subscriber/create")
    public ResponseEntity<UserDTO> createSubscriber(@Validated(OnCreate.class) @RequestBody UserDTO userDTO){
        return ResponseEntity.ok(userService.createUser(userDTO, Role.SUBSCRIBER));
    }

    @DeleteMapping("subscriber/delete/{id}")
    public ResponseEntity<UserDTO> deactivateSubscriber(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("subscriber/update/{id}")
    public ResponseEntity<UserDTO> updateSubscriber(@PathVariable Long id, @Validated(OnPutSingleUpdate.class) @RequestBody UserDTO userDTO){
        return ResponseEntity.ok(userService.updateUser(id, userDTO));
    }

    @GetMapping("subscriber/list-all")
    public PagedModel<UserDTO> findAllSubscribersSummary(@PageableDefault(page = 0, size = 20, sort = "lastName", direction = Sort.Direction.ASC) Pageable pageable){
        return new PagedModel<>(userService.findAllSummary(pageable));
    }

    @GetMapping("subscriber/{id}")
    public ResponseEntity<UserDTO> findSubscriberById(@PathVariable Long id){
        return ResponseEntity.ok(userService.findById(id));
    }



}
