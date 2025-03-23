package org.example.assigment.controller.auth;

import org.example.assigment.dto.LoginRequestDTO;
import org.example.assigment.model.auth.MyUser;
import org.example.assigment.service.auth.JwtService;
import org.example.assigment.service.auth.MyUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    private final MyUserService myUserService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public RegistrationController(MyUserService myUserService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.myUserService = myUserService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/api/register")
    public ResponseEntity<MyUser> createUser(@RequestBody MyUser user) {
        return ResponseEntity.ok(myUserService.saveUser(user));
    }

    @PostMapping("/api/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequestDTO user) {
        System.out.println("Logging in user: " + user.getUsername());
        // use AuthenticationManager to authenticate user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );
        // check if authentication is successful
        if (authentication.isAuthenticated()) {
            // return user token that was created
            return ResponseEntity.ok(jwtService.generateToken(myUserService.loadUserByUsername(user.getUsername())));
        } else {
            System.out.println("Authentication failed for user: " + user.getUsername());
            return ResponseEntity.status(401).body("Authentication failed");
        }
    }

}
