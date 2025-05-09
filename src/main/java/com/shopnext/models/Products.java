package com.shopnext.models;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Products")
public class Products {

   public String _id;

   public String name;

   public String category;

   public int price;

   public String description;

   public String imgPath;

   public float rating;

}
