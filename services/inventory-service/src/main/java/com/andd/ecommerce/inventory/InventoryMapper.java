package com.andd.ecommerce.inventory;

import org.springframework.stereotype.Service;

@Service
public class InventoryMapper {
    public InventoryResponse toInventoryResponse(Inventory inventory) {
        return new InventoryResponse(
                inventory.getSkuCode(),
                inventory.getQuantity() > 0
        );
    }
}
