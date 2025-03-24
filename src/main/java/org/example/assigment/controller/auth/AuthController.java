package org.example.assigment.controller.auth;

import jakarta.persistence.EntityNotFoundException;
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

    /**
     * Registers a new user as a MEMBER.
     * This endpoint is open for public registration and automatically assigns the MEMBER role.
     *
     * @param request the registration data containing username and password
     * @return HTTP 201 with the newly registered user info
     */
    @PostMapping("/api/register")
    public ResponseEntity<RegisterResponseDTO> registerMember(@Validated @RequestBody RegisterRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(myUserService.saveUser(request.getUsername(), request.getPassword()));
    }

    /**
     * Registers a new user with a specified role.
     * This endpoint is restricted to admin use and allows setting ADMIN, LIBRARIAN, or MEMBER roles.
     *
     * @param request the registration data including username, password, and role
     * @return HTTP 201 with the newly registered user info
     */
    @PostMapping("/api/admin/register")
    public ResponseEntity<RegisterResponseDTO> createUserByAdmin(@Validated @RequestBody AdminRegisterRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(myUserService.saveUser(request.getUsername(), request.getPassword(), request.getRole()));
    }

    /**
     * Authenticates a user and returns a JWT token upon success.
     *
     * @param user the login credentials (username and password)
     * @return JWT token if authenticated, or HTTP 401 if failed
     */
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

    /**
     * Creates a new user with the LIBRARIAN role.
     * This endpoint is used by admins to register librarians.
     *
     * @param request the registration data with username and password
     * @return HTTP 201 with the new librarian’s info, or appropriate error response
     */
    // create librarian
    @PostMapping("/api/librarians")
    public ResponseEntity<?> createLibrarian(@Validated @RequestBody RegisterRequestDTO request) {
        try {
            RegisterResponseDTO newLibrarian = myUserService.createLibrarian(request.getUsername(), request.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(newLibrarian);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    /**
     * Updates the username and password of a librarian by ID.
     * Validates that the user is a librarian before updating.
     *
     * @param id      the ID of the librarian to update
     * @param request the new username and password
     * @return updated librarian info, or appropriate error response
     */
    // update librarian
    @PutMapping("/api/librarians/{id}")
    public ResponseEntity<?> updateLibrarian(@PathVariable Long id, @Validated @RequestBody RegisterRequestDTO request) {
        try {
            UpdateUserResponseDTO updatedLibrarian = myUserService.updateLibrarian(id, request.getUsername(), request.getPassword());
            return ResponseEntity.ok(updatedLibrarian);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    /**
     * Deletes a librarian by ID.
     * Ensures that the user has the LIBRARIAN role before deletion.
     *
     * @param id the ID of the librarian to delete
     * @return success message or appropriate error response
     */
    // delete librarian
    @DeleteMapping("/api/librarians/{id}")
    public ResponseEntity<?> deleteLibrarian(@PathVariable Long id) {
        try {
            myUserService.deleteLibrarian(id);
            return ResponseEntity.ok("Librarian deleted successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
