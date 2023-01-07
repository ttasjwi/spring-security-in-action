package com.ttasjwi.ssia.product.domain.entity;

import com.ttasjwi.ssia.product.domain.vo.Money;
import com.ttasjwi.ssia.product.domain.vo.ProductId;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Product {

    private ProductId id;
    private String name;
    private Money price;

    @Builder
    public Product(ProductId id, String name, Money price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
