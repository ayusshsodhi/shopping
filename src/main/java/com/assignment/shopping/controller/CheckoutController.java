package com.assignment.shopping.controller;

import com.assignment.shopping.model.pricing.BuyXGetYFree;
import com.assignment.shopping.model.pricing.PricingAssignment;
import com.assignment.shopping.model.pricing.XForThePriceOfY;
import com.assignment.shopping.service.CheckoutService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
public class CheckoutController {

    private final CheckoutService checkoutService;

    // This is just put here for ease of testing of the scenario of the assignment. We can have it as an input as well
    List<PricingAssignment> pricingAssignments = List.of(new PricingAssignment(new BuyXGetYFree(1,1), "Melon"),
            new PricingAssignment(new XForThePriceOfY(3,2), "Lime"));

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    /** API which accepts Names of Items in a List from a basket
     *
     * @param basket
     * @return
     */
    @GetMapping(value = "/checkout", consumes = MediaType.APPLICATION_JSON_VALUE)
    public BigDecimal calculateBasketPrice(@RequestBody @Valid List<String> basket){
        return checkoutService.calculateBasketPrice(basket,pricingAssignments);
    }
}
