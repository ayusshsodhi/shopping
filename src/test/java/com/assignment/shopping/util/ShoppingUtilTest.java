package com.assignment.shopping.util;

import com.assignment.shopping.model.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
public class ShoppingUtilTest {

    private ShoppingUtil shoppingUtil;

    @BeforeEach
    public void setup(){
        shoppingUtil = new ShoppingUtil();
    }

    @Test
    void isValid_ShouldReturnParsedItemsList(){
        List<String> basket = List.of("Apple", "Banana", "Melon", "Lime", "Melon", "Melon","Lime","Lime","Lime","Lime");
        List<Item> result = shoppingUtil.parseInputToBasketItems(basket);
        Assertions.assertTrue(result.size() > 0);
    }

    @Test
    void throwsException_ShouldThrowExceptionForInvalidBasketItems(){
        List<String> invalidItemsInBasket = List.of("Apple", "Banana", "Melon", "Lime", "Melon", "Melon","Lime","Lime","Lime","Lime","Lime","ime");
        Assertions.assertThrows(IllegalArgumentException.class, () -> shoppingUtil.parseInputToBasketItems(invalidItemsInBasket));
    }



}
