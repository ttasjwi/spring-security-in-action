package com.ttasjwi.ssia.product.application.ports.output;

import com.ttasjwi.ssia.product.domain.entity.Product;

import java.util.List;

public interface ProductOutputPort {

    List<Product> findAll();
}
