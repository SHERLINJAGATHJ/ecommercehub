package com.sherlin.ecommercehub.controller;

import com.sherlin.ecommercehub.dto.LoginRequest;
import com.sherlin.ecommercehub.dto.LoginResponse;
import com.sherlin.ecommercehub.dto.RegisterRequest;
import com.sherlin.ecommercehub.dto.UserResponse;
import com.sherlin.ecommercehub.entity.User;
import com.sherlin.ecommercehub.service.AuthService;
import com.sherlin.ecommercehub.service.JwtService;
import com.sherlin.ecommercehub.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;
    private final JwtService jwtService;

    public AuthController(UserService userService,AuthService authService,JwtService jwtService){
        this.userService=userService;
        this.authService=authService;
        this.jwtService=jwtService;
    }


    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request){

        UserResponse response=userService.registerUser(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request){

        User user=authService.authenticate(request);

        String token=jwtService.generateToken(user);

        LoginResponse response=new LoginResponse(token,"Bearer");

        return ResponseEntity.ok(response);

    }
}
