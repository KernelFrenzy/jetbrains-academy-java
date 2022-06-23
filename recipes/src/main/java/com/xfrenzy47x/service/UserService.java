package com.xfrenzy47x.service;

import com.xfrenzy47x.dto.RegisterRequestDto;
import com.xfrenzy47x.model.User;
import com.xfrenzy47x.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public boolean addUser(RegisterRequestDto registerRequestDto) {
        User user = new User(registerRequestDto.getEmail(), encoder.encode(registerRequestDto.getPassword()), "ROLE_USER");
        userRepository.save(user);
        return true;
    }
}

