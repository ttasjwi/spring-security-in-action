package com.ttasjwi.ssia.product.adapter.input.web.dto;

import com.ttasjwi.ssia.product.domain.entity.Product;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ProductDto {

    private String name;
    private BigDecimal price;

    public ProductDto(Product product) {
        this.name = product.getName();
        this.price = product.getPrice().getAmount();
    }

}
