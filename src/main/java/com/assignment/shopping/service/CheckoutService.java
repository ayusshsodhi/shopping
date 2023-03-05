package com.assignment.shopping.service;

import com.assignment.shopping.model.Item;
import com.assignment.shopping.model.pricing.NoDiscount;
import com.assignment.shopping.model.pricing.PricingAssignment;
import com.assignment.shopping.util.ShoppingUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
@Slf4j
public class CheckoutService {

    private final ShoppingUtil shoppingUtil;

    public CheckoutService(ShoppingUtil shoppingUtil) {
        this.shoppingUtil = shoppingUtil;
    }

    /** Calculates the Basket Price based on Basket Items and Offers
     *
     * @param basketItems
     * @param pricingAssignments
     * @return
     */
    public BigDecimal calculateBasketPrice(List<String> basketItems, List<PricingAssignment> pricingAssignments){

        List<Item> itemList = shoppingUtil.parseInputToBasketItems(basketItems);

        ConcurrentMap<String, Integer> discountedItemsTracker = new ConcurrentHashMap<>();
        BigDecimal totalPrice = itemList.stream().map(i -> {
            Optional<PricingAssignment> pricingAssignment = pricingAssignments.stream().filter(x -> x.item().equals(i.name())).findFirst();
            if(pricingAssignment.isPresent()){
                return pricingAssignment.get().pricing().apply(i, discountedItemsTracker);
            }
            return new NoDiscount().apply(i, discountedItemsTracker);
        }).reduce(BigDecimal.ZERO, BigDecimal::add);
        log.info("Total price for the basked is {} p", totalPrice);
        return totalPrice;
    }
}
