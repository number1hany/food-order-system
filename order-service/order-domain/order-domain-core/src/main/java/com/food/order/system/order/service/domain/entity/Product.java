package com.food.order.system.order.service.domain.entity;

import com.food.order.system.domain.entity.BaseEntity;
import com.food.order.system.valueobject.Money;
import com.food.order.system.valueobject.ProductId;

public class Product extends BaseEntity<ProductId> {

  private final String name;
  private final Money money;

  public Product(ProductId productId, String name, Money money) {
    super.setId(productId);
    this.name = name;
    this.money = money;
  }

  public String getName() {
    return name;
  }

  public Money getMoney() {
    return money;
  }
}
