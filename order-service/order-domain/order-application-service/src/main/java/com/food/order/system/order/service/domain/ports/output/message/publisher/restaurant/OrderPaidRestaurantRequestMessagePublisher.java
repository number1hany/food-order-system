package com.food.order.system.order.service.domain.ports.output.message.publisher.restaurant;

import com.food.order.system.event.publisher.DomainEventPublisher;
import com.food.order.system.order.service.domain.event.OrderPaidEvent;

public interface OrderPaidRestaurantRequestMessagePublisher extends DomainEventPublisher<OrderPaidEvent> {

}
