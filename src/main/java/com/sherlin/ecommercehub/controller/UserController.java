package com.sherlin.ecommercehub.controller;

import com.sherlin.ecommercehub.dto.UserResponse;
import com.sherlin.ecommercehub.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService=userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(@AuthenticationPrincipal Jwt jwt){

        Long userId=Long.valueOf(jwt.getSubject());

        UserResponse user=userService.getUserById(userId);

        return ResponseEntity.ok(user);
    }
}
