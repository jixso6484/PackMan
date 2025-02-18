package com.packman.controller;

import org.springframework.web.bind.annotation.RestController;

import com.packman.DTO.Oauth.User_Register_Dto;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class AuthController {
    
    
    @GetMapping("/signup")
    public String getMethodName(@Valid String param) {
    
    }
    
}
