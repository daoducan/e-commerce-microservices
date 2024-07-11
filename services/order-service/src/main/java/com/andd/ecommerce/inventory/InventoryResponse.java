package com.andd.ecommerce.inventory;

public record InventoryResponse(
        String skuCode,
        boolean isInStock
) {
}
