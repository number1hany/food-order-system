package com.food.order.system.order.service.domain.valueobject;

import com.food.order.system.domain.entity.BaseEntity;
import com.food.order.system.valueobject.BaseId;
import java.util.UUID;

public class TrackingId extends BaseId<UUID> {
  protected TrackingId(UUID value) {
    super(value);
  }
}
