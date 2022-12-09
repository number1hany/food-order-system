package com.food.order.system.order.service.domain.ports.output.repository;

import com.food.order.system.order.service.domain.entity.Order;
import com.food.order.system.order.service.domain.valueobject.TrackingId;
import java.util.Optional;

public interface OrderRepository {

  Order save(Order order);

  Optional<Order> findByTrackingId(TrackingId trackingId);


}
