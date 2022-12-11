package com.food.ordering.system.valueobject;

import java.util.UUID;

public class ProductId<T> extends BaseId<UUID> {

  public ProductId(UUID value) {
    super(value);
  }
}
