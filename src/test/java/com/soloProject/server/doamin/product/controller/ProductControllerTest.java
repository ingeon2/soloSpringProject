package com.soloProject.server.doamin.product.controller;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;

import com.google.gson.Gson;
import com.soloProject.server.domain.product.dto.ProductDto;
import com.soloProject.server.domain.product.entity.Product;
import com.soloProject.server.domain.product.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private ProductService productService;

    @Test
    void postProductTest() throws Exception {
        // given
        ProductDto.Create productCreateDto = ProductDto.Create.builder()
                .name("노트북")
                .sellPrice(120)
                .buyPrice(100)
                .quantity(1000)
                .build();

        Product createProduct = new Product();
        createProduct.setProductId(1L);


        given(productService.createProduct(Mockito.any(ProductDto.Create.class)))
                .willReturn(createProduct);

        String content = gson.toJson(productCreateDto);


        // when
        ResultActions actions =
                mockMvc.perform(
                        post("/products")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                );

        // then
        actions
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", is(startsWith("/products"))));
    }

    @Test
    public void sellProductTest() throws Exception {
        // Given
        long memberId = 1;
        long productId = 1;
        int quantity = 10;

        Product createProduct = new Product();
        createProduct.setProductId(1L); // 생성된 멤버의 ID 설정

        given(productService.sellProduct(Mockito.any(Long.class), Mockito.any(Long.class), Mockito.any(Integer.class)))
                .willReturn(createProduct); // createMember 메서드의 동작 정의

        // When
        ResultActions actions =
                mockMvc.perform(patch("/products/sell/{product-id}/{member-id}/{quantity}", productId, memberId, quantity)
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        actions
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", startsWith("/products")));
    }

}
