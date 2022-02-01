package com.cscb869.carserviceserver.payloads;

public class JWTAuthenticationResponse {

    private String token;
    private String role;

    public JWTAuthenticationResponse(String token, String role) {
        this.token = token;
        this.role = role;
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
}
