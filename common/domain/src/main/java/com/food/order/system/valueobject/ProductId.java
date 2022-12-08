package com.food.order.system.valueobject;

import java.util.UUID;

public class ProductId<T> extends BaseId<UUID> {

  public ProductId(UUID value) {
    super(value);
  }
}
