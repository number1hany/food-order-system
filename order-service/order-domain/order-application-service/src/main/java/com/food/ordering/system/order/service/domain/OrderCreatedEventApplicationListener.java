package com.food.ordering.system.order.service.domain;

import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.food.ordering.system.order.service.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class OrderCreatedEventApplicationListener {
  private final OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher;

  public OrderCreatedEventApplicationListener(OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher) {
    this.orderCreatedPaymentRequestMessagePublisher = orderCreatedPaymentRequestMessagePublisher;
  }

  /**
   * OrderCreateCommandHandler.createOrder 메서드가 완료되고 트랜잭션이 완료될 때만 처리된다.
   * @param orderCreatedEvent
   */
  @TransactionalEventListener
  void process(OrderCreatedEvent orderCreatedEvent) {
    orderCreatedPaymentRequestMessagePublisher.publish(orderCreatedEvent);
  }
}
