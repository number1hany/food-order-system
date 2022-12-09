package com.food.order.system.order.service.domain.dto.track;

import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TracOrderQuery {
  @NotNull
  private final UUID orderTrackingId;
}
