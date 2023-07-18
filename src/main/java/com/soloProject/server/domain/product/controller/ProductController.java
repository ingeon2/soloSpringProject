package com.soloProject.server.domain.product.controller;

import com.soloProject.server.domain.product.dto.ProductDto;
import com.soloProject.server.domain.product.entity.Product;
import com.soloProject.server.domain.product.service.ProductService;
import com.soloProject.server.global.utils.UriCreator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;


@RestController
@Slf4j
@Validated
@Transactional
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final static String PRODUCT_DEFAULT_URL = "/products";

    @Autowired
    ProductService productService;

    @PostMapping
    public ResponseEntity postProduct(@Valid @RequestBody ProductDto.Create productPostDto) {
        Product createProduct = productService.createProduct(productPostDto);
        URI location = UriCreator.createUri(PRODUCT_DEFAULT_URL, createProduct.getProductId());
        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/sell/{product-id}/{member-id}/{quantity}")
    public ResponseEntity sellProduct(@PathVariable("member-id") @Positive long memberId,
                                              @PathVariable("product-id") @Positive long productId,
                                              @PathVariable("quantity") @Positive int quantity) {
        Product patchProduct = productService.sellProduct(memberId, productId, quantity);

        URI location = UriCreator.createUri(PRODUCT_DEFAULT_URL, patchProduct.getProductId());
        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/buy/{product-id}/{member-id}/{quantity}")
    public ResponseEntity buyProduct(@PathVariable("member-id") @Positive long memberId,
                                              @PathVariable("product-id") @Positive long productId,
                                              @PathVariable("quantity") @Positive int quantity) {
        Product patchProduct = productService.buyProduct(memberId, productId, quantity);

        URI location = UriCreator.createUri(PRODUCT_DEFAULT_URL, patchProduct.getProductId());
        return ResponseEntity.created(location).build();
    }


}
