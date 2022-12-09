package com.food.order.system.order.service.domain.entity;

import com.food.order.system.domain.entity.BaseEntity;
import com.food.order.system.valueobject.Money;
import com.food.order.system.valueobject.ProductId;

public class Product extends BaseEntity<ProductId> {

  private final String name;
  private final Money price;

  public Product(ProductId productId, String name, Money price) {
    super.setId(productId);
    this.name = name;
    this.price = price;
  }

  public String getName() {
    return name;
  }

  public Money getPrice() {
    return price;
  }
}
