package com.soloProject.server.domain.product.dto;

import io.swagger.annotations.ApiModelProperty;
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
        @ApiModelProperty(example = "물건 이름")
        private String name;

        @ApiModelProperty(example = "구매가")
        private int buyPrice;

        @ApiModelProperty(example = "판매가")
        private int sellPrice;

        @ApiModelProperty(example = "재고 수량")
        private int quantity;
    }
}
