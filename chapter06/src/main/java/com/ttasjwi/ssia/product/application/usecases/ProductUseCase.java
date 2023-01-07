package com.ttasjwi.ssia.product.application.usecases;

import com.ttasjwi.ssia.product.domain.entity.Product;

import java.util.List;

public interface ProductUseCase {

    List<Product> findAll();
}
