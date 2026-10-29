package com.sherlin.ecommercehub.service;

import com.sherlin.ecommercehub.dto.RegisterRequest;
import com.sherlin.ecommercehub.dto.UserResponse;
import com.sherlin.ecommercehub.entity.User;
import com.sherlin.ecommercehub.repository.UserRepository;
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
}
