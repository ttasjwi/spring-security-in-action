package com.ttasjwi.ssia.product.adapter.output.persistence.repository;

import com.ttasjwi.ssia.product.adapter.output.persistence.data.ProductData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductData, Long> {

}
