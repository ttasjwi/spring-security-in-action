package com.ttasjwi.ssia.application.usecases;

import com.ttasjwi.ssia.domain.entity.Product;

import java.util.List;

public interface ProductUseCase {

    List<Product> findAll();
}
