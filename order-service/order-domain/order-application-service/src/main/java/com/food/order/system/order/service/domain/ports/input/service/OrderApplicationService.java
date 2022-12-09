package com.food.order.system.order.service.domain.ports.input.service;

import com.food.order.system.order.service.domain.dto.create.CreateOrderCommand;
import com.food.order.system.order.service.domain.dto.create.CreateOrderResponse;
import com.food.order.system.order.service.domain.dto.track.TracOrderQuery;
import com.food.order.system.order.service.domain.dto.track.TrackOrderResponse;
import javax.validation.Valid;

public interface OrderApplicationService {

  CreateOrderResponse createOrder(@Valid CreateOrderCommand createOrderCommand);

  TrackOrderResponse trackOrder(@Valid TracOrderQuery tracOrderQuery);
}
