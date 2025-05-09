package com.shopnext.controller;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import com.shopnext.business_logic.Auth;
import com.shopnext.business_logic.RegisterUser;
import com.shopnext.models.User;

@RestController()
public class UserController {
    @Autowired
    RegisterUser registerUser;
    @Autowired
    Auth Auth;

    @CrossOrigin(origins = "*")
    @GetMapping("/auth")
    public User getinfo(@RequestHeader("Authorization") String token) throws Exception {
        return Auth.getUser(token);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/register")
    public User registerUser(@RequestBody User user) throws Exception {
        return registerUser.register(user);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/gregister")
    public User gregisterUser(@RequestHeader("Authorization") String token) throws Exception {

        return registerUser.gregister(token);
    }
}
