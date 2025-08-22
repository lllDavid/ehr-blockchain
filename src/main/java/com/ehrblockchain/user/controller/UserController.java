package com.ehrblockchain.user.controller;

import java.util.List;

import com.ehrblockchain.security.role.RoleEnum;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ehrblockchain.user.service.UserService;
import com.ehrblockchain.user.dto.UserCreateDTO;
import com.ehrblockchain.user.dto.UserUpdateDTO;
import com.ehrblockchain.user.dto.UserDTO;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PreAuthorize("hasRole('ADMIN') or #id == principal.id")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserCreateDTO userCreateDTO) {
        UserDTO savedUserDTO = userService.createUser(userCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUserDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserCreateDTO userCreateDTO) {
        userCreateDTO.setRoleName(RoleEnum.PATIENT);
        UserDTO savedUserDTO = userService.createUser(userCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUserDTO);
    }

    @PreAuthorize("hasRole('ADMIN') or #id == principal.id")
    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id,
                                              @RequestBody UserUpdateDTO updateDTO) {
        UserDTO updatedUserDto = userService.updateUser(id, updateDTO);
        return ResponseEntity.ok(updatedUserDto);
    }

    @PreAuthorize("hasRole('ADMIN') or #id == principal.id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}