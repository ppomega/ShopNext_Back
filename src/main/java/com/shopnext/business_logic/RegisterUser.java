package com.shopnext.business_logic;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.shopnext.models.User;
import com.shopnext.repos.UserRepo;

@Service
public class RegisterUser {
    @Autowired
    UserRepo userRepo;

    @Autowired
    Auth auth;

    public User register(User user) {
        User existingUser = userRepo.findByEmail(user.email);

        if (existingUser != null) {
            return existingUser;
        }

        return userRepo.save(user);

    }

    public User gregister(String token) throws Exception {
        HashMap<String, String> map = auth.getUserInfo(token);
        User existingUser = userRepo.findByEmail(map.get("email"));

        if (existingUser != null) {
            return existingUser;
        }
        User user = new User();
        user.name = map.get("name");
        user.email = map.get("email");
        user.password = map.get("");
        user.mobile = map.get("");
        return userRepo.save(user);

    }
}