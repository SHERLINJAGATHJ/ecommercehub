package com.sherlin.ecommercehub.service;

import com.sherlin.ecommercehub.dto.RegisterRequest;
import com.sherlin.ecommercehub.dto.UserResponse;
import com.sherlin.ecommercehub.entity.User;
import com.sherlin.ecommercehub.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder){
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
    }

    public UserResponse registerUser(RegisterRequest request) {
        String email = request.email()
                .trim()
                .toLowerCase(Locale.ROOT);

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already registered");
        }


        String hashedPassword = passwordEncoder.encode(request.password());

        User user = new User(
                request.name().trim(),
                email,
                hashedPassword
                );

        User savedUser=userRepository.save(user);

        return new UserResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail()
        );
    }

    public UserResponse getUserById(Long userId){

        User user=userRepository.findById(userId)
                .orElseThrow(()->
                        new org.springframework.web.server.ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User not found"
                        )
                );

        return new UserResponse(user.getId(),user.getName(),user.getEmail());
    }
}
