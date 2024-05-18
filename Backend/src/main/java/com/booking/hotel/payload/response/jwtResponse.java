package com.booking.hotel.payload.response;

public class jwtResponse {

    private Long id;
    private String email;
    private String token;
    private String role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public jwtResponse(Long id, String email, String token, String role) {
        this.id = id;
        this.email = email;
        this.token = token;
        this.role = role;
    }
}
