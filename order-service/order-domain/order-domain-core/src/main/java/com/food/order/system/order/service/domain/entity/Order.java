package com.food.order.system.order.service.domain.entity;

import com.food.order.system.domain.entity.AggregateRoot;
import com.food.order.system.order.service.domain.valueobject.StreetAdress;
import com.food.order.system.order.service.domain.valueobject.TrackingId;
import com.food.order.system.valueobject.CustomerId;
import com.food.order.system.valueobject.Money;
import com.food.order.system.valueobject.OrderId;
import com.food.order.system.valueobject.OrderStatus;
import com.food.order.system.valueobject.RestaurantId;
import java.util.List;

public class Order extends AggregateRoot<OrderId> {

  private final CustomerId customerId;
  private final RestaurantId restaurantId;
  private final StreetAdress streetAdress;
  private final Money money;
  private final List<OrderItem> items;

  private TrackingId trackingId;
  private OrderStatus orderStatus;
  private List<String> failureMessages;

  /**
   * 생성자는 private 이므로, Builder를 사용해서 값을 주입해야함
   * @param builder
   */
  private Order(Builder builder) {
    super.setId(builder.orderId);
    customerId = builder.customerId;
    restaurantId = builder.restaurantId;
    streetAdress = builder.streetAdress;
    money = builder.money;
    items = builder.items;
    trackingId = builder.trackingId;
    orderStatus = builder.orderStatus;
    failureMessages = builder.failureMessages;
  }


  public CustomerId getCustomerId() {
    return customerId;
  }

  public RestaurantId getRestaurantId() {
    return restaurantId;
  }

  public StreetAdress getStreetAdress() {
    return streetAdress;
  }

  public Money getMoney() {
    return money;
  }

  public List<OrderItem> getItems() {
    return items;
  }

  public TrackingId getTrackingId() {
    return trackingId;
  }

  public OrderStatus getOrderStatus() {
    return orderStatus;
  }

  public List<String> getFailureMessages() {
    return failureMessages;
  }

  public static final class Builder {

    private OrderId orderId;
    private CustomerId customerId;
    private RestaurantId restaurantId;
    private StreetAdress streetAdress;
    private Money money;
    private List<OrderItem> items;
    private TrackingId trackingId;
    private OrderStatus orderStatus;
    private List<String> failureMessages;

    private Builder() {
    }

    public static Builder newBuilder() {
      return new Builder();
    }

    public Builder orderId(OrderId val) {
      orderId = val;
      return this;
    }

    public Builder customerId(CustomerId val) {
      customerId = val;
      return this;
    }

    public Builder restaurantId(RestaurantId val) {
      restaurantId = val;
      return this;
    }

    public Builder streetAdress(StreetAdress val) {
      streetAdress = val;
      return this;
    }

    public Builder money(Money val) {
      money = val;
      return this;
    }

    public Builder items(List<OrderItem> val) {
      items = val;
      return this;
    }

    public Builder trackingId(TrackingId val) {
      trackingId = val;
      return this;
    }

    public Builder orderStatus(OrderStatus val) {
      orderStatus = val;
      return this;
    }

    public Builder failureMessages(List<String> val) {
      failureMessages = val;
      return this;
    }

    public Order build() {
      return new Order(this);
    }
  }
}
