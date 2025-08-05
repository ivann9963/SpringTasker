package com.example.springtasker.controller;

import com.example.springtasker.config.DataLoader;
import com.example.springtasker.model.Role;
import com.example.springtasker.model.User;
import com.example.springtasker.repo.RoleRepository;
import com.example.springtasker.repo.UserRepository;
import com.example.springtasker.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository users;
    private final RoleRepository roles;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository users,
                          RoleRepository roles,
                          PasswordEncoder encoder,
                          AuthenticationManager authManager,
                          UserDetailsService userDetailsService,
                          JwtUtil jwtUtil) {
        this.users = users;
        this.roles = roles;
        this.encoder = encoder;
        this.authManager = authManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        if (users.findByUsername(req.username()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }
        Role userRole = roles.findByName(DataLoader.ROLE_USER).orElse(null);
        if (userRole == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Default role not found");
        }
        User user = new User();
        user.setUsername(req.username());
        user.setPassword(encoder.encode(req.password()));
        user.setRoles(Set.of(userRole));
        users.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        try {
            // 1. Authenticate the user using username and password
            authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.username(), req.password()));
            // 2. If authentication is successful, load user details
            UserDetails userDetails = userDetailsService.loadUserByUsername(req.username());
            // 3. Generate a JWT token for the user
            String jwt = jwtUtil.generateToken(userDetails);
            // 4. Return the token in the response (as JSON)
            return ResponseEntity.ok().body(java.util.Map.of("token", jwt));
        } catch (Exception e) {
            // If authentication fails, return 401 Unauthorized
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

}
