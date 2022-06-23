package com.xfrenzy47x.controller;

import com.xfrenzy47x.dto.RegisterRequestDto;
import com.xfrenzy47x.model.User;
import com.xfrenzy47x.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/api/register")
    public ResponseEntity<Object> register(@Validated @RequestBody RegisterRequestDto registerRequestDto) {
        User user = userService.findByUsername(registerRequestDto.getEmail());
        if (user != null) {
            return ResponseEntity.status(400).build();
        } else {
            userService.addUser(registerRequestDto);
            return ResponseEntity.ok().build();
        }
    }
}

