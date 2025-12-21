// src/main/java/com/example/demo/dto/JwtResponse.java
package com.example.demo.dto;

public class JwtResponse {
    private String token;
    private Long userId;
    private String email;
    private String role;

    public JwtResponse() {}

    public JwtResponse(String token, Long userId, String email, String role) {
        this.token = token;
        this.userId = userId;
        this.email = email;
        this.role = role;
    }

    // getters and setters
}
