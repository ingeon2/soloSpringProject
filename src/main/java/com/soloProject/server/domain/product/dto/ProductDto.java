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
        private String name;

        private int buyPrice;

        private int sellPrice;

        private int quantity;
    }
}
