package com.ehrblockchain.auth.service;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ehrblockchain.user.dto.UserDTO;
import com.ehrblockchain.user.service.UserService;;

@Service
public class AuthService {

    private final UserService userService;

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public UserDTO getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
            return null;
        }
        String email = auth.getName();
        return userService.getUserByEmail(email);
    }


    /*
    public void sendPasswordResetToken(String email) {
    }

    public void resetPassword(ResetPasswordDTO dto) {

    }
    public void sendEmailVerification(String email) {
    }

    public void verifyEmail(EmailVerificationDTO dto) {

    }

    */
}