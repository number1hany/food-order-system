package com.food.order.system.order.service.domain.event;

import com.food.order.system.event.DomainEvent;
import com.food.order.system.order.service.domain.entity.Order;
import java.time.ZonedDateTime;

public abstract class OrderEvent implements DomainEvent<Order> {
  private final Order order;
  private final ZonedDateTime createdAt;

  public OrderEvent(Order order, ZonedDateTime createdAt) {
    this.order = order;
    this.createdAt = createdAt;
  }

  public Order getOrder() {
    return order;
  }

  public ZonedDateTime getCreatedAt() {
    return createdAt;
  }

}
