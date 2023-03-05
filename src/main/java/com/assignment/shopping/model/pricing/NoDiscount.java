package com.assignment.shopping.model.pricing;

import com.assignment.shopping.model.Item;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentMap;

public class NoDiscount implements Pricing {

    /** Applies the unit price when no discount/offer is specified
     *
     * @param item
     * @param discountedItemsTracker
     * @return
     */
    @Override
    public BigDecimal apply(Item item, ConcurrentMap<String, Integer> discountedItemsTracker) {
        return item.unitPrice();
    }
}
