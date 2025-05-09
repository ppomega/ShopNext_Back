package com.shopnext.business_logic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.shopnext.config.CustomPageImpl;
import com.shopnext.config.FilterEntity;
import com.shopnext.models.Products;
import com.shopnext.repos.ProductRepo;

@Service
public class Filter {
    @Autowired
    ProductRepo productRepo;
    @Autowired
    CustomPageImpl pg;

    @Autowired
    public FilterEntity fe;

    public Page<Products> filterByCategories(PageRequest p) {
        List<Products> filteredProducts = new ArrayList<>();
        for (String category : fe.categories) {

            filteredProducts.addAll(productRepo.findAllByCategory(category));

        }
        return pg.pagefetcher(filteredProducts, p);
    }

    public Page<Products> filterByCategoriesAndPrice(PageRequest p) {
        List<Products> filteredProducts = new ArrayList<>();
        for (String category : fe.categories) {

            filteredProducts
                    .addAll(productRepo.findAllByCategoryAndPriceLessThan(category, fe.price));

        }
        return pg.pagefetcher(filteredProducts, p);

    }

    public Page<Products> filterByPrice(PageRequest p) {

        return pg.pagefetcher(productRepo.findAllByPriceLessThan(fe.price), p);
    }

    public Page<Products> noFilter(PageRequest p) {

        return pg.pagefetcher((List<Products>) productRepo.findAll(), p);
    }

}
