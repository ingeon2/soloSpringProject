package com.soloProject.server.doamin.product.service;

import com.soloProject.server.domain.product.repository.ProductRepository;
import com.soloProject.server.domain.product.service.ProductService;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductServiceTest {

    private ProductService productService;
    private ProductRepository productRepository;

    @BeforeEach
    void setup() {
        productService = new ProductService();

    }

    @Test
    void createProductTest() {

    }


}
