package com.ttasjwi.ssia.product.domain.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class ProductId {

    private Long id;

    public ProductId(Long id) {
        this.id = id;
    }

}
