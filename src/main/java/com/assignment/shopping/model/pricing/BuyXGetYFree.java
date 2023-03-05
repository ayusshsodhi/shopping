package com.assignment.shopping.model.pricing;

import com.assignment.shopping.model.Item;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

@Slf4j
public class BuyXGetYFree implements Pricing {

    private final int buy;
    private final int free;

    public BuyXGetYFree(int buy, int free) {
        if(buy < 1 || free < 1 || free > buy) {
            log.error("Invalid Parameters provided for BuyXGetYFree pricing : buy {}, free {}", buy, free);
            throw new InvalidParameterException("Invalid Parameters provided for BuyXGetYFree pricing");
        }
        this.buy = buy;
        this.free = free;
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
        if(itemCount != null && itemCount + 1 <= buy){
            discountedItemsTracker.put(item.name(), itemCount+1);
            return item.unitPrice();
        } else if (itemCount != null && itemCount + 1 > buy && itemCount + 1 <= (buy+free)) {
            discountedItemsTracker.put(item.name(), itemCount+1);
            return BigDecimal.ZERO;
        } else {
            discountedItemsTracker.put(item.name(), 1);
            return item.unitPrice();
        }
    }
}
