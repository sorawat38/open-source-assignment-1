package org.example.assigment.controller.auth;

import org.example.assigment.dto.auth.*;
import org.example.assigment.service.auth.JwtService;
import org.example.assigment.service.auth.MyUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    private final MyUserService myUserService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthController(MyUserService myUserService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.myUserService = myUserService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/api/register")
    public ResponseEntity<RegisterResponseDTO> createUser(@Validated @RequestBody RegisterRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(myUserService.saveUser(request));
    }

    @PostMapping("/api/login")
    public ResponseEntity<?> loginUser(@Validated @RequestBody LoginRequestDTO user) {
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

    // update librarian
    @PutMapping("/api/librarians/{id}")
    public ResponseEntity<?> updateLibrarian(@PathVariable Long id, @RequestBody UpdateLibrarianRequestDTO request) {
        try {
            UpdateUserResponseDTO updatedLibrarian = myUserService.updateUser(id, request.getUsername(), request.getPassword(), request.getRole());
            return ResponseEntity.ok(updatedLibrarian);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    // delete librarian
}
