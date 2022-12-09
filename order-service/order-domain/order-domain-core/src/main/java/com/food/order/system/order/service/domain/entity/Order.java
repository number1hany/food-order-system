package com.food.order.system.order.service.domain.entity;

import com.food.order.system.domain.entity.AggregateRoot;
import com.food.order.system.order.service.domain.exception.OrderDomainException;
import com.food.order.system.order.service.domain.valueobject.OrderItemId;
import com.food.order.system.order.service.domain.valueobject.StreetAdress;
import com.food.order.system.order.service.domain.valueobject.TrackingId;
import com.food.order.system.valueobject.CustomerId;
import com.food.order.system.valueobject.Money;
import com.food.order.system.valueobject.OrderId;
import com.food.order.system.valueobject.OrderStatus;
import com.food.order.system.valueobject.RestaurantId;
import java.util.List;
import java.util.UUID;

public class Order extends AggregateRoot<OrderId> {

  private final CustomerId customerId;
  private final RestaurantId restaurantId;
  private final StreetAdress streetAdress;
  private final Money price;
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
    price = builder.price;
    items = builder.items;
    trackingId = builder.trackingId;
    orderStatus = builder.orderStatus;
    failureMessages = builder.failureMessages;
  }

  public void initializeOrder() {
    super.setId(new OrderId(UUID.randomUUID()));
    trackingId = new TrackingId(UUID.randomUUID());
    orderStatus = OrderStatus.PENDING;
    initializeOrderItems();
  }

  public void validateOrder() {
    validateInitialOrder();
    validateTotalPrice();
    validateItemsPrice();
  }

  private void validateItemsPrice() {
    Money orderItemsTotal = items.stream().map(orderItem -> {
      validateItemsPrice(orderItem);
      return orderItem.getSubTotal();
    }).reduce(Money.ZERO, Money::add);

    if (!price.equals(orderItemsTotal)) {
      throw new OrderDomainException("Total price: " + price.getAmount()
          + " is not equal to Order items total: " + orderItemsTotal.getAmount() + "!");
    }
  }

  private void validateItemsPrice(OrderItem orderItem) {
    if (!orderItem.isPriceValid()) {
      throw new OrderDomainException("Order item price: " + orderItem.getPrice().getAmount() +
          "is not valid for product " + orderItem.getProduct().getId().getValue());
    }
  }

  private void validateTotalPrice() {
    if (price == null || !price.isGreaterThanZero()) {
      throw new OrderDomainException("Total price must be greater than zero!");
    }
  }


  private void validateInitialOrder() {
    if (orderStatus != null || getId() != null) {
      throw new OrderDomainException("Order is not in correct state for initializetion!");
    }
  }

  private void initializeOrderItems() {
    long itemId = 1;
    for (OrderItem orderItem : items) {
      orderItem.initializeOrderItem(super.getId(), new OrderItemId(itemId++));
    }
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

  public Money getPrice() {
    return price;
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

  /**
   * PENDING 상태이면
   * 결제 상태로 변경
   */
  public void pay() {
    if (orderStatus != OrderStatus.PENDING) {
      throw new OrderDomainException("Order is not in correct state for pay operation!");
    }
    orderStatus = OrderStatus.PAID;
  }

  public void approve() {
    if (orderStatus != OrderStatus.PAID) {
      throw new OrderDomainException("Order is not in correct state for approve operation!");
    }
    orderStatus = OrderStatus.APPROVED;
  }

  /**
   * 취소 상태로 설정
   * 레스토랑에서 취소될 경우
   * 주문 상태를 취소로 설정하고
   * 결제 서비스에 취소 요청을 보내야 한다
   */
  public void initCancel(List<String> failureMessages) {
    if (orderStatus != OrderStatus.PAID) {
      throw new OrderDomainException("Order is not in correct state for approve operation!");
    }
    orderStatus = OrderStatus.CANCELLING;
    updateFailureMessages(failureMessages);
  }

  private void updateFailureMessages(List<String> failureMessages) {
    if (this.failureMessages != null && failureMessages != null) {
      this.failureMessages.addAll(failureMessages.stream()
          .filter(message -> !message.isEmpty())
          .toList());
    }
    if (this.failureMessages == null) {
      this.failureMessages = failureMessages;
    }
  }

  /**
   * 주문 취소
   * 1) 결제가 취소된 경우 PENDING 상태로 변경
   * 2) 레스토랑에서 취소된 경우 결제 취소 후 PENDING 상태로 변경
   */
  public void cancel(List<String> failureMessages) {
    if (orderStatus != OrderStatus.CANCELLING || orderStatus == OrderStatus.PENDING) {
      throw new OrderDomainException("Order is not in correct state for cancel operation!");
    }
    orderStatus = OrderStatus.CANCELLED;
    updateFailureMessages(failureMessages);
  }

  public static final class Builder {

    private OrderId orderId;
    private CustomerId customerId;
    private RestaurantId restaurantId;
    private StreetAdress streetAdress;
    private Money price;
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

    public Builder price(Money val) {
      price = val;
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
