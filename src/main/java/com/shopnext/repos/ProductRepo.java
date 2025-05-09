package com.shopnext.repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.shopnext.models.Products;

public interface ProductRepo extends CrudRepository<Products, String> {

    public List<Products> findAllByCategory(String category); // Method

    public List<Products> findAllByPriceLessThan(int price); // Method

    public List<Products> findAllByCategoryAndPriceLessThan(String category, int price); // Method
}
