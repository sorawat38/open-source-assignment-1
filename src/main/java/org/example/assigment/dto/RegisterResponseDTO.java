package org.example.assigment.dto;

public class RegisterResponseDTO {
    private String username;
    private String role;

    public RegisterResponseDTO(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }
}
