package com.ehrblockchain.security.config;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.ehrblockchain.user.repository.UserRepository;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Value("${app.security.user.name}")
    private String username;

    @Value("${app.security.user.password}")
    private String password;

    @Value("${app.security.user.role}")
    private String role;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http    // csrf disabled for testing
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/register").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginProcessingUrl("/auth/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .successHandler((req, res, auth) -> {
                            res.setStatus(HttpServletResponse.SC_OK);
                        })
                        .failureHandler((req, res, ex) -> {
                            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication failed");
                        })
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessHandler((req, res, auth) -> res.setStatus(HttpServletResponse.SC_OK))
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        InMemoryUserDetailsManager uds = new InMemoryUserDetailsManager();
        uds.createUser(User.withUsername(username)
                .password(passwordEncoder().encode(password))
                .roles(role)
                .build()
        );

        return usernameInput -> {
            try {
                return userRepository.findByEmail(usernameInput)
                        .map(user -> User.withUsername(user.getEmail())
                                .password(user.getPassword())
                                .roles(user.getRole().getName().name())
                                .build())
                        .orElseThrow(() -> new UsernameNotFoundException(usernameInput));
            } catch (UsernameNotFoundException e) {
                return uds.loadUserByUsername(usernameInput);
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder(16,
                32,
                2,
                65536,
                3);
    }
}