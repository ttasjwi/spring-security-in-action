package com.ttasjwi.ssia.adapter.output.persistence.data;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name= "product")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private CurrencyData currency;

    @Builder
    public ProductData(Long id, String name, BigDecimal price, CurrencyData currency) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.currency = currency;
    }
}
