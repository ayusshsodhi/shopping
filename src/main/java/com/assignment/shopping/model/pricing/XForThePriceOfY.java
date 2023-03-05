package com.assignment.shopping.model.pricing;

import com.assignment.shopping.model.Item;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.concurrent.ConcurrentMap;

@Slf4j
public class XForThePriceOfY implements Pricing {
    private final int totalItems;
    private final int pricedItemCount;

    public XForThePriceOfY(int totalItems, int pricedItemCount) {
        if (totalItems <= pricedItemCount) {
            log.error("Invalid Parameters provided for XForThePriceOfY pricing : totalItems {}, pricedItemCount {}", totalItems, pricedItemCount);
            throw new InvalidParameterException("Invalid Parameters provided for XForThePriceOfY pricing");
        }
        this.totalItems = totalItems;
        this.pricedItemCount = pricedItemCount;
    }

    /** Calculates the Discounts to be applied according to the BuyXGetYFree offer running
     *
     * @param item
     * @param discountedItemsTracker
     * @return
     */
    @Override
    public BigDecimal apply(Item item, ConcurrentMap<String, Integer> discountedItemsTracker) {
        Integer itemCount = discountedItemsTracker.get(item.name());
        if (itemCount != null && itemCount + 1 <= pricedItemCount) {
            discountedItemsTracker.put(item.name(), itemCount + 1);
            return item.unitPrice();
        } else if (itemCount != null && itemCount + 1 > pricedItemCount && itemCount + 1 <= (totalItems)) {
            discountedItemsTracker.put(item.name(), itemCount + 1);
            return BigDecimal.ZERO;
        } else {
            discountedItemsTracker.put(item.name(), 1);
            return item.unitPrice();
        }
    }
}
