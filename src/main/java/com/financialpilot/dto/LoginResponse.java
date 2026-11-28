package com.financialpilot.dto;

public class LoginResponse {

    private int userId;
    private String name;
    private String email;
    private String accessToken;

    public LoginResponse() {
    }

    public LoginResponse(int userId,
                         String name,
                         String email,
                         String accessToken) {

        this.userId = userId;
        this.name = name;
        this.email = email;
        this.accessToken = accessToken;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}