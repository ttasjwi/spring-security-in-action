package com.ttasjwi.ssia.product.adapter.output.persistence.mapper;

import com.ttasjwi.ssia.product.adapter.output.persistence.data.ProductData;
import com.ttasjwi.ssia.product.domain.entity.Product;
import com.ttasjwi.ssia.product.domain.vo.Currency;
import com.ttasjwi.ssia.product.domain.vo.Money;
import com.ttasjwi.ssia.product.domain.vo.ProductId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    public List<Product> mapToDomainEntities(List<ProductData> products) {
        return products.stream()
                .map(this::mapToDomainEntity)
                .collect(Collectors.toList());
    }

    public Product mapToDomainEntity(ProductData data) {
        return Product.builder()
                .id(new ProductId(data.getId()))
                .name(data.getName())
                .price(new Money(data.getPrice(), Currency.valueOf(data.getCurrency().name())))
                .build();
    }
}
