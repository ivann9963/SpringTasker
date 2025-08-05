package com.example.springtasker.controller;

import com.example.springtasker.config.DataLoader;
import com.example.springtasker.model.Role;
import com.example.springtasker.model.User;
import com.example.springtasker.repo.RoleRepository;
import com.example.springtasker.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

    public AuthController(UserRepository users,
                          RoleRepository roles,
                          PasswordEncoder encoder,
                          AuthenticationManager authManager) {
        this.users = users;
        this.roles = roles;
        this.encoder = encoder;
        this.authManager = authManager;
    }


    @PostMapping("/regiser")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        // 1) check if users.findByUsername(req.getUsername()).isPresent() → return 400
        // 2) fetch ROLE_USER: roles.findByName("ROLE_USER")
        // 3) new User(u, encoder.encode(req.getPassword()), Set.of(role))
        // 4) users.save(newUser)
        // 5) return 201 CREATED
        if(users.findByUsername(req.username()).isPresent()){
            return ResponseEntity.badRequest().body("Username already exist");
        }
        User user = new User();
        user.setUsername(req.username());
        user.setPassword(encoder.encode(req.password()));
        Role userRole = roles.findByName(DataLoader.ROLE_USER).get();
        user.setRoles(Set.of(userRole));
        users.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.username(), req.password()));

        // 1) authManager.authenticate(
        //       new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
        // 2) if success, return 200 OK (you can return a simple message or a JWT here)
    }

}
