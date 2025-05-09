package com.shopnext.controller;

import org.springframework.web.bind.annotation.RestController;

import com.shopnext.business_logic.Filter;
import com.shopnext.config.FilterEntity;
import com.shopnext.models.Products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class DbController {
    @Autowired
    Filter filter;

    @CrossOrigin(origins = "*")
    @PostMapping("/fetchProducts/{number}")
    public Page<Products> getProductsbyPriceAndCategory(@PathVariable("number") int number,
            @RequestBody FilterEntity fe) {
        filter.fe.categories = fe.categories;
        filter.fe.price = fe.price;
        PageRequest a = PageRequest.of(number, 6, Sort.by(Sort.Direction.DESC, "price"));
        if (fe.categories != null && fe.categories.length != 0 && fe.price != 0) {
            return filter.filterByCategoriesAndPrice(a);
        }
        if (fe.price != 0) {
            return filter.filterByPrice(a);
        }
        if (fe.categories != null) {
            return filter.filterByCategories(a);
        }
        return filter.noFilter(a);
    }

}