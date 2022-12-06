package com.food.order.system.valueobject;

import java.util.UUID;

public class RestaurantId<T> extends BaseId<UUID>{
  public RestaurantId(UUID value) {
    super(value);
  }
}
