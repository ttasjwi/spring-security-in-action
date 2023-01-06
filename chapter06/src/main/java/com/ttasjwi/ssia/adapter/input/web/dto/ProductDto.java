package com.ttasjwi.ssia.adapter.input.web.dto;

import com.ttasjwi.ssia.domain.entity.Product;
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
