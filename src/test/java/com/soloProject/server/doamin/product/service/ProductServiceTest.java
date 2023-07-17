package com.soloProject.server.doamin.product.service;

import com.soloProject.server.domain.balance.service.BalanceService;
import com.soloProject.server.domain.member.entity.Member;
import com.soloProject.server.domain.member.service.MemberService;
import com.soloProject.server.domain.product.dto.ProductDto;
import com.soloProject.server.domain.product.entity.Product;
import com.soloProject.server.domain.product.repository.ProductRepository;
import com.soloProject.server.domain.product.service.ProductService;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
@SqlGroup({
        @Sql(value = "/sql/product-service-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BalanceService balanceService;


    @Test
    void createProductTest() {
        //given
        ProductDto.Create productCreateDto = ProductDto.Create.builder()
                .productId(1L)
                .name("물건")
                .buyPrice(100)
                .sellPrice(120)
                .quantity(10)
                .build();

        //when
        Product result = productService.createProduct(productCreateDto);

        //then
        assertThat(result).isNotNull();
    }

    @Test
    void sellProductTest() {
        //given
        long productId = 1; //처음 물건 양 100개, 판매가 120, 구매가 100
        long memberId = 1; //처음 멤버 result 0
        int quantity = 10;

        //when
        productService.sellProduct(memberId, productId, quantity);

        Product resultProduct = productService.findVerifiedProduct(productId);
        Member resultMember = memberService.findVerifiedMember(memberId);
        int balance = balanceService.getBalance();

        //then
        assertThat(resultProduct.getQuantity()).isEqualTo(90);
        assertThat(resultMember.getResult()).isEqualTo(1200);
        assertThat(balance).isEqualTo(1200);

    }



}
