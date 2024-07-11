package com.andd.ecommerce.order;

import com.andd.ecommerce.orderline.OrderLineItemsDto;

import java.util.List;

public record OrderRequest(
        List<OrderLineItemsDto> orderLineItemsDtoList
) {
}
