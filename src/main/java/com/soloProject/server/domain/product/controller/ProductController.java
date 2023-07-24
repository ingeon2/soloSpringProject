package com.soloProject.server.domain.product.controller;

import com.soloProject.server.domain.product.dto.ProductDto;
import com.soloProject.server.domain.product.entity.Product;
import com.soloProject.server.domain.product.service.ProductService;
import com.soloProject.server.global.utils.UriCreator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/products") //
@Api(tags = {"재고 API Test"})  // Swagger 최상단 Controller 명칭
public class ProductController {
    private final static String PRODUCT_DEFAULT_URL = "/products";

    @Autowired
    ProductService productService;

    @PostMapping
    @ApiOperation(value = "물건 등록", notes = "물건 등록 API")
    public ResponseEntity postProduct(@Valid @RequestBody ProductDto.Create productPostDto) {
        Product createProduct = productService.createProduct(productPostDto);
        URI location = UriCreator.createUri(PRODUCT_DEFAULT_URL, createProduct.getProductId());
        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/sell/{product-id}/{member-id}/{quantity}")
    @ApiOperation(value = "재고 판매", notes = "재고 판매 API") // Swagger에 사용하는 API에 대한 간단 설명
    @ApiImplicitParams( // Swagger에 사용하는 파라미터들에 대해 설명
            {
                    @ApiImplicitParam(name = "member-id", value = "멤버 아이디"),
                    @ApiImplicitParam(name = "product-id", value = "재고 물품 아이디"),
                    @ApiImplicitParam(name = "quantity", value = "판매 재고 수량")
            })
    public ResponseEntity sellProduct(@PathVariable("member-id") @Positive long memberId,
                                              @PathVariable("product-id") @Positive long productId,
                                              @PathVariable("quantity") @Positive int quantity) {
        Product patchProduct = productService.sellProduct(memberId, productId, quantity);

        URI location = UriCreator.createUri(PRODUCT_DEFAULT_URL, patchProduct.getProductId());
        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/buy/{product-id}/{member-id}/{quantity}")
    @ApiOperation(value = "재고 구매", notes = "재고 구매 API")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "member-id", value = "멤버 아이디"),
                    @ApiImplicitParam(name = "product-id", value = "재고 물품 아이디"),
                    @ApiImplicitParam(name = "quantity", value = "구매 재고 수량")
            })
    public ResponseEntity buyProduct(@PathVariable("member-id") @Positive long memberId,
                                              @PathVariable("product-id") @Positive long productId,
                                              @PathVariable("quantity") @Positive int quantity) {
        Product patchProduct = productService.buyProduct(memberId, productId, quantity);

        URI location = UriCreator.createUri(PRODUCT_DEFAULT_URL, patchProduct.getProductId());
        return ResponseEntity.created(location).build();
    }


}
