package com.assignment.shopping.util;

import com.assignment.shopping.model.Item;
import com.assignment.shopping.model.Items;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.util.EnumUtils;

import java.util.List;

@Slf4j
@Component
public class ShoppingUtil {

    /** Converts the Basket Item Names to Item Object
     *
     * @param inputList
     * @return
     */
    public List<Item> parseInputToBasketItems(List<String> inputList){
        return inputList.stream().map(i -> {
            Items item;
            try {
                item = EnumUtils.findEnumInsensitiveCase(Items.class, i);
            } catch (IllegalArgumentException e) {
                log.error("Invalid Item provided in Basket {}", i);
                throw e;
            }
            return new Item(item.getName(), item.getUnitPrice());
        }).toList();
    }
}
