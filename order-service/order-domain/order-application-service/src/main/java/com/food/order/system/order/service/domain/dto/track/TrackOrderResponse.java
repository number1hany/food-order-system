package com.food.order.system.order.service.domain.dto.track;

import com.food.order.system.valueobject.OrderStatus;
import java.util.List;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TrackOrderResponse {
  @NotNull
  private final UUID orderTrackingId;
  @NotNull
  private final OrderStatus orderStatus;
  private final List<String> failureMessages;
}
