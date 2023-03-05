package com.assignment.shopping.controller;

import com.assignment.shopping.model.Item;
import com.assignment.shopping.model.Items;
import com.assignment.shopping.model.pricing.BuyXGetYFree;
import com.assignment.shopping.model.pricing.PricingAssignment;
import com.assignment.shopping.model.pricing.XForThePriceOfY;
import com.assignment.shopping.service.CheckoutService;
import com.assignment.shopping.util.ShoppingUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
public class CheckoutControllerTest {

    private CheckoutController checkoutController;
    private CheckoutService checkoutService;
    @Mock
    private ShoppingUtil shoppingUtil;

    @BeforeEach
    public void setup(){
        checkoutService = new CheckoutService(shoppingUtil);
        checkoutController = new CheckoutController(checkoutService);
    }

    @Test
    void isValid_ShouldCalculateBasketPriceForActualScenario(){
        List<Item> basket = List.of(new Item(Items.APPLE.getName(), Items.APPLE.getUnitPrice())
                , new Item(Items.BANANA.getName(), Items.BANANA.getUnitPrice())
                , new Item(Items.MELON.getName(), Items.MELON.getUnitPrice())
                , new Item(Items.MELON.getName(), Items.MELON.getUnitPrice())
                , new Item(Items.LIME.getName(), Items.LIME.getUnitPrice()));
        List<PricingAssignment> pricingAssignments = List.of(new PricingAssignment(new BuyXGetYFree(1,1), "Melon"),
                new PricingAssignment(new XForThePriceOfY(3,2), "Lime"));
        Mockito.when(shoppingUtil.parseInputToBasketItems(ArgumentMatchers.any())).thenReturn(basket);
        BigDecimal result = checkoutController.calculateBasketPrice(new ArrayList<>());
        Assertions.assertEquals(new BigDecimal(120), result);
    }

    @Test
    void isValid_ShouldCalculateBasketPriceForDifferentScenario(){
        List<Item> basket = List.of(new Item(Items.APPLE.getName(), Items.APPLE.getUnitPrice())
                , new Item(Items.BANANA.getName(), Items.BANANA.getUnitPrice())
                , new Item(Items.MELON.getName(), Items.MELON.getUnitPrice())
                , new Item(Items.MELON.getName(), Items.MELON.getUnitPrice())
                , new Item(Items.LIME.getName(), Items.LIME.getUnitPrice())
                , new Item(Items.LIME.getName(), Items.LIME.getUnitPrice())
                , new Item(Items.LIME.getName(), Items.LIME.getUnitPrice())
                , new Item(Items.MELON.getName(), Items.MELON.getUnitPrice())
                , new Item(Items.LIME.getName(), Items.LIME.getUnitPrice()));
        List<PricingAssignment> pricingAssignments = List.of(new PricingAssignment(new BuyXGetYFree(2,1), "Melon"),
                new PricingAssignment(new XForThePriceOfY(4,3), "Lime"));
        ReflectionTestUtils.setField(checkoutController,"pricingAssignments", pricingAssignments );
        Mockito.when(shoppingUtil.parseInputToBasketItems(ArgumentMatchers.any())).thenReturn(basket);
        BigDecimal result = checkoutController.calculateBasketPrice(new ArrayList<>());
        Assertions.assertEquals(new BigDecimal(200), result);
    }
}
