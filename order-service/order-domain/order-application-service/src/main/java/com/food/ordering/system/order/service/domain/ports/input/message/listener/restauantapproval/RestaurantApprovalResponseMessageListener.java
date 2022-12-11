package com.food.ordering.system.order.service.domain.ports.input.message.listener.restauantapproval;

import com.food.ordering.system.order.service.domain.dto.message.RestaurantApprovalResponse;

/**
 * 레스토랑 승인을 위한 메세지 리스너
 */
public interface RestaurantApprovalResponseMessageListener {
  void orderApproved(RestaurantApprovalResponse restaurantApprovalResponse);
  void orderRejected(RestaurantApprovalResponse restaurantApprovalResponse);
}
