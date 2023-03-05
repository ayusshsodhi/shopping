package com.assignment.shopping.model.pricing;

import com.assignment.shopping.model.Item;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentMap;

public interface Pricing {
    BigDecimal apply(Item item, ConcurrentMap<String, Integer> discountedItems);
}
