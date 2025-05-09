package com.shopnext.models;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Users")
public class User {
    public String _id;
    public String name;
    public String email;
    public String password;
    public String mobile;

}
