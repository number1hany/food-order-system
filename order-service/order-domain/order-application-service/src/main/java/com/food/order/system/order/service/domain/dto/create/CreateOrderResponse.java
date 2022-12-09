package com.food.order.system.order.service.domain.dto.create;

import com.food.order.system.valueobject.OrderStatus;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CreateOrderResponse {
  @NotNull
  private final UUID orderTrackingId;
  @NotNull
  private final OrderStatus orderStatus;
  @NotNull
  private final String message;
}
