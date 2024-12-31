package com.example.lab09.controllers;


import com.example.lab09.model.account.Account;
import com.example.lab09.model.account.AccountService;

import com.example.lab09.payload.ApiResponse;
import com.example.lab09.payload.LoginRequest;
import com.example.lab09.payload.LoginResponse;
import com.example.lab09.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/account")
class AccountController {

    @Autowired
    private AccountService services;

    @Autowired
    AuthenticationManager authentication;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    JwtUtil jwtUtil;

    @GetMapping("")
    public List<Account> getAll(){
        return services.getAllAccount();
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody Account account){

        if(services.findByUsername(account.getUsername()) != null){
            return ResponseEntity.ok().body("Username is existed");
        }
        ApiResponse response =  ApiResponse.builder()
                .code(200)
                .message("Login Successfully")
                .Object(services.addAccount(account)).build();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest account){
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(account.getUsername(), account.getPassword());

        authentication.authenticate(token);
        Account acc = services.findByUsername(account.getUsername());
        String jwtToken = jwtUtil.generateToken(userDetailsService.loadUserByUsername(account.getUsername()));
        ApiResponse response =  ApiResponse.builder()
                    .code(200)
                    .message("Login Successfully")
                    .Object(LoginResponse.builder().account(acc).jwtToken(jwtToken).build()).build();
        return ResponseEntity.ok().body(response);
    }
}



