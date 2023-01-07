package com.ttasjwi.ssia.application.ports.output;

import com.ttasjwi.ssia.domain.entity.Product;

import java.util.List;

public interface ProductOutputPort {

    List<Product> findAll();
}
