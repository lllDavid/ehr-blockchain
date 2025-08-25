package com.ehrblockchain.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        UserDTO savedUserDTO = userService.createUser(userCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUserDTO);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> profile() {
        UserDTO dto = authService.getCurrentUser();
        return ResponseEntity.ok(dto);
    }


    /*
    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(@RequestParam String email) {
        authService.sendPasswordResetToken(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestBody ResetPasswordDTO dto) {
        authService.resetPassword(dto);
        return ResponseEntity.ok().build();
    }

     @PostMapping("/send-verification")
    public ResponseEntity<Void> sendEmailVerification(@RequestParam String email) {
        authService.sendEmailVerification(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verify-email")
    public ResponseEntity<Void> verifyEmail(@RequestBody EmailVerificationDTO dto) {
        authService.verifyEmail(dto);
        return ResponseEntity.ok().build();
    }
    */

}