package com.shopnext.repos;

import org.springframework.data.repository.CrudRepository;

import com.shopnext.models.User;

public interface UserRepo extends CrudRepository<User, String> {
    User findByEmail(String email);

    User findByMobile(String mobile);

    User findByName(String name);
}
