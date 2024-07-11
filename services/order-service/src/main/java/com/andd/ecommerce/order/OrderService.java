package com.andd.ecommerce.order;

import com.andd.ecommerce.inventory.InventoryResponse;
import com.andd.ecommerce.orderline.OrderLineItems;
import com.andd.ecommerce.orderline.OrderLineItemsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final WebClient.Builder webClientBuilder;

    public String placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItemsList =  orderRequest.orderLineItemsDtoList().stream()
                .map(orderMapper::mapToDto)
                .toList();
        order.setOrderLineItemsList(orderLineItemsList);

        List<String> skuCodes = order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getSkuCode)
                .toList();
        //Call Inventory service and place order if it is in Stock
        InventoryResponse[] inventoryResponsesArr = webClientBuilder.build().get()
                .uri("http://inventory-service/api/v1/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        boolean allProductsIsInStock = Arrays.stream(inventoryResponsesArr)
                .allMatch(InventoryResponse::isInStock);
        if (allProductsIsInStock) {
            orderRepository.save(order);
            return "Order placed successfully. New!";
        } else {
            throw new IllegalArgumentException("Product is not in stock. Please try again later");
        }
    }
}
