package com.food.order.system.order.service.domain.ports.output.message.publisher.payment;

import com.food.order.system.event.publisher.DomainEventPublisher;
import com.food.order.system.order.service.domain.event.OrderCreatedEvent;

public interface OrderCreatedPaymentRequestMessagePublisher extends DomainEventPublisher<OrderCreatedEvent> {

}
