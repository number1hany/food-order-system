package com.food.order.system.valueobject;

import java.util.UUID;

public class CustomerId<T> extends BaseId<UUID> {

  public CustomerId(UUID value) {
    super(value);
  }

}
