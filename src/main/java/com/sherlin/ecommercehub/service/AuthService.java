package com.sherlin.ecommercehub.service;

import com.sherlin.ecommercehub.dto.LoginRequest;
import com.sherlin.ecommercehub.entity.User;
import com.sherlin.ecommercehub.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository,PasswordEncoder passwordEncoder){
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
    }

    public User authenticate(LoginRequest request){

        String email=request.email().trim().toLowerCase(Locale.ROOT);

        User user=userRepository.findByEmail(email)
                .orElseThrow(()->new IllegalArgumentException("Invalid email or password"));

        if(!passwordEncoder.matches(request.password(),user.getPassword())){
            throw new IllegalArgumentException("Invalid email or password");
        }
        return user;
    }
}
