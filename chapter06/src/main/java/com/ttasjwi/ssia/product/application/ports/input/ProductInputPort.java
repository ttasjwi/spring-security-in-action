package com.ttasjwi.ssia.product.application.ports.input;

import com.ttasjwi.ssia.product.application.ports.output.ProductOutputPort;
import com.ttasjwi.ssia.product.application.usecases.ProductUseCase;
import com.ttasjwi.ssia.product.domain.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductInputPort implements ProductUseCase {

    private final ProductOutputPort productOutputPort;

    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productOutputPort.findAll();
    }
}
