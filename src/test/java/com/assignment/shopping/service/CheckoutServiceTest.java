package com.assignment.shopping.service;

import com.assignment.shopping.model.pricing.BuyXGetYFree;
import com.assignment.shopping.model.pricing.PricingAssignment;
import com.assignment.shopping.model.pricing.XForThePriceOfY;
import com.assignment.shopping.util.ShoppingUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class CheckoutServiceTest {

    private CheckoutService checkoutService;
    private ShoppingUtil shoppingUtil;

    @BeforeEach
    public void setup(){
        shoppingUtil = new ShoppingUtil();
        checkoutService = new CheckoutService(shoppingUtil);
    }

    @Test
    void isValid_ShouldCalculateBasketPriceForAssignmentScenario(){
        List<String> basket = List.of("Apple", "Banana", "Melon", "Lime", "Melon", "Melon","Lime","Lime","Lime","Lime");
        List<PricingAssignment> pricingAssignments = List.of(new PricingAssignment(new BuyXGetYFree(1,1), "Melon"),
                new PricingAssignment(new XForThePriceOfY(3,2), "Lime"));
        BigDecimal result = checkoutService.calculateBasketPrice(basket, pricingAssignments);
        Assertions.assertEquals(new BigDecimal(215), result);
    }

    @Test
    void isValid_ShouldCalculateBasketPriceForDifferentDiscountScenario(){
        List<String> basketOne = List.of("Apple", "Banana", "Melon", "Lime", "Melon", "Melon","Lime","Lime","Lime","Lime","Lime","Lime");
        List<PricingAssignment> pricingAssignmentsOne = List.of(new PricingAssignment(new BuyXGetYFree(2,1), "Melon"),
                new PricingAssignment(new XForThePriceOfY(5,3), "Lime"));
        BigDecimal result = checkoutService.calculateBasketPrice(basketOne, pricingAssignmentsOne);
        Assertions.assertEquals(new BigDecimal(230), result);
    }

    @Test
    void throwsException_ShouldThrowExceptionForInvalidBasketItems(){
        List<String> invalidItemsInBasket = List.of("Apple", "Banana", "Melon", "Lime", "Melon", "Melon","Lime","Lime","Lime","Lime","Lime","ime");
        Assertions.assertThrows(IllegalArgumentException.class, () -> checkoutService.calculateBasketPrice(invalidItemsInBasket, new ArrayList<>()));
    }

    @Test
    void throwsException_ShouldThrowExceptionForInvalidDiscountOfferForBuyXGetYFree(){
        Assertions.assertThrows(InvalidParameterException.class, () -> new PricingAssignment(new BuyXGetYFree(2,3), "Melon"));
    }

    @Test
    void throwsException_ShouldThrowExceptionForInvalidDiscountOfferForXForThePriceOfY(){
        Assertions.assertThrows(InvalidParameterException.class, () -> new PricingAssignment(new XForThePriceOfY(4,4), "Melon"));
    }

}
