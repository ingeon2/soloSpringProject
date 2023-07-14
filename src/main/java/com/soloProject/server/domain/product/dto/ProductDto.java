package com.soloProject.server.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

public class ProductDto {

    @Getter
    @AllArgsConstructor
    public static class Post {
        @NotBlank
        private String name;

        @NotBlank
        private int buyPrice;

        @NotBlank
        private int sellPrice;
    }
}
