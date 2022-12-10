package com.food.order.system.order.service.domain.mapper;

import com.food.order.system.order.service.domain.dto.create.CreateOrderCommand;
import com.food.order.system.order.service.domain.dto.create.CreateOrderResponse;
import com.food.order.system.order.service.domain.dto.create.OrderAddress;
import com.food.order.system.order.service.domain.entity.Order;
import com.food.order.system.order.service.domain.entity.OrderItem;
import com.food.order.system.order.service.domain.entity.Product;
import com.food.order.system.order.service.domain.entity.Restaurant;
import com.food.order.system.order.service.domain.valueobject.StreetAdress;
import com.food.order.system.valueobject.CustomerId;
import com.food.order.system.valueobject.Money;
import com.food.order.system.valueobject.ProductId;
import com.food.order.system.valueobject.RestaurantId;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class OrderDataMapper {
  public Restaurant createOrderCommandToRestaurant(CreateOrderCommand createOrderCommand) {
    return Restaurant.builder()
        .restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
        .products(createOrderCommand.getItems()
            .stream()
            .map(orderItem ->
                new Product(new ProductId(orderItem.getProductId())))
            .collect(Collectors.toList()))
        .build();
  }

  public Order createOrderCommandToOrder(CreateOrderCommand createOrderCommand) {
    return Order.builder()
        .customerId(new CustomerId(createOrderCommand.getCustomerId()))
        .deliveryAddress(orderAddressToStreetAddress(createOrderCommand.getAddress()))
        .price(new Money(createOrderCommand.getPrice()))
        .items(orderItemsToOrderItemEntities(createOrderCommand.getItems()))
        .build();
  }

  private List<OrderItem> orderItemsToOrderItemEntities(List<com.food.order.system.order.service.domain.dto.create.OrderItem> orderItems) {
    return orderItems.stream()
        .map(orderItem -> OrderItem.builder()
            .product(new Product(new ProductId(orderItem.getProductId())))
            .price(new Money(orderItem.getPrice()))
            .quantity(orderItem.getQuantity())
            .subTotal(new Money(orderItem.getSubTotal()))
            .build())
        .collect(Collectors.toList());
  }

  private StreetAdress orderAddressToStreetAddress(OrderAddress orderAddress) {
    return new StreetAdress(UUID.randomUUID(), orderAddress.getStreet(), orderAddress.getPostalCode(), orderAddress.getCity()
    );
  }

  public CreateOrderResponse orderToCreateOrderResponse(Order order) {
    return CreateOrderResponse.builder()
        .orderTrackingId(order.getTrackingId().getValue())
        .orderStatus(order.getOrderStatus())
        .build();
  }
}
