package com.ehrblockchain.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ehrblockchain.security.role.RoleEnum;
import com.ehrblockchain.user.dto.UserCreateDTO;
import com.ehrblockchain.user.dto.UserDTO;
import com.ehrblockchain.user.service.UserService;
import com.ehrblockchain.auth.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    public AuthController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserCreateDTO userCreateDTO) {
        userCreateDTO.setRoleName(RoleEnum.PATIENT);
        UserDTO savedUserDTO = userService.createUser(userCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUserDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest,
                                               HttpServletRequest request,
                                               HttpServletResponse response) {
        LoginResponse loginResponse = authService.authenticate(loginRequest, request, response);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        authService.logout(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> me() {
        UserDTO dto = authService.getCurrentUserDto();
        return ResponseEntity.ok(dto);
    }
}