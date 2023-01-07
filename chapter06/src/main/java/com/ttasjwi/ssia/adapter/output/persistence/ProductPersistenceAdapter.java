package com.ttasjwi.ssia.adapter.output.persistence;

import com.ttasjwi.ssia.adapter.output.persistence.data.ProductData;
import com.ttasjwi.ssia.adapter.output.persistence.mapper.ProductMapper;
import com.ttasjwi.ssia.adapter.output.persistence.repository.ProductRepository;
import com.ttasjwi.ssia.application.ports.output.ProductOutputPort;
import com.ttasjwi.ssia.domain.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ProductPersistenceAdapter implements ProductOutputPort {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public List<Product> findAll() {
        List<ProductData> products = productRepository.findAll();
        return productMapper.mapToDomainEntities(products);
    }
}
