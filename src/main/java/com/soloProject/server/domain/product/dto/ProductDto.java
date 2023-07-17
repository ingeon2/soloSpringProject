package com.soloProject.server.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

public class ProductDto {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Create {
        @NotBlank
        private long productId;

        @NotBlank
        private String name;

        @NotBlank
        private int buyPrice;

        @NotBlank
        private int sellPrice;

        @NotBlank
        private int quantity;
    }
}
