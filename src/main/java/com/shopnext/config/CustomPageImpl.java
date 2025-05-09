package com.shopnext.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.shopnext.models.Products;

@Configuration
public class CustomPageImpl {

    public Page<Products> pagefetcher(List<Products> list, PageRequest pr) {
        return new PageImpl<>(list.subList(pr.getPageNumber() * pr.getPageSize(),
                Math.min(((pr.getPageNumber() * pr.getPageSize()) + pr.getPageSize()), list.size())), pr, list.size());

    }

}
