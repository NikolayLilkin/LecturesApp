package com.lecturesapp.dtos;

import lombok.Data;

@Data
public class LoginResponseDTO {
    private boolean success;
    private String message;
    private String token;
    private String username;
    private String password;
}