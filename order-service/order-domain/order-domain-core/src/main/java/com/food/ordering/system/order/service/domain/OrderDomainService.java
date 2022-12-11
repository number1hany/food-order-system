package com.food.ordering.system.order.service.domain;

import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.entity.Restaurant;
import com.food.ordering.system.order.service.domain.event.OrderCancelledEvent;
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.food.ordering.system.order.service.domain.event.OrderPaidEvent;
import java.util.List;

public interface OrderDomainService {

  // 주문 확인
  OrderCreatedEvent validateAndInitiateOrder(Order order, Restaurant restaurant);

  // 결제
  OrderPaidEvent payOrder(Order order);

  // 주문 승인
  void approveOrder(Order order);

  // 주문 결제 취소
  OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages);

  // 주문 취소
  void cancelOrder(Order order,List<String> failureMessages);
}
