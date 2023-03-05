package com.assignment.shopping.model;

import java.math.BigDecimal;

// Inventory of valid Items and prices
public enum Items {
    APPLE("Apple", new BigDecimal(35)),
    BANANA("Banana", new BigDecimal(20)),
    MELON("Melon", new BigDecimal(50)),
    LIME("Lime", new BigDecimal(15));

    private String name;
    private BigDecimal unitPrice;

    Items(String name, BigDecimal unitPrice) {
        this.name = name;
        this.unitPrice = unitPrice;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }
}
