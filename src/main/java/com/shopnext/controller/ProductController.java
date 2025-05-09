package com.shopnext.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.shopnext.config.R2Req;

@RestController
public class ProductController {
    @Autowired
    R2Req r;

    @CrossOrigin(origins = "*")
    @GetMapping("/image/{c}")
    public ResponseEntity<byte[]> getImages(@PathVariable("c") String c) throws Exception {
        System.out.println(c);
        return r.getRes(c);
    }

}
