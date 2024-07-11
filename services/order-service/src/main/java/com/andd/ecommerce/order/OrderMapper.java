package com.andd.ecommerce.order;

import com.andd.ecommerce.orderline.OrderLineItems;
import com.andd.ecommerce.orderline.OrderLineItemsDto;
import org.springframework.stereotype.Service;

@Service
public class OrderMapper {
    public OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        return OrderLineItems.builder()
                .skuCode(orderLineItemsDto.skuCode())
                .price(orderLineItemsDto.price())
                .quantity(orderLineItemsDto.quantity())
                .build();
    }
}
