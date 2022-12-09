package com.food.order.system.order.service.domain.ports.input.message.listener.payment;

import com.food.order.system.order.service.domain.dto.message.PaymentResponse;

/**
 * 결제 승인을 위한 메세지 리스너
 */
public interface PaymentResponseMessageListener {
  void paymentCompleted(PaymentResponse paymentResponse);
  void paymentCancelled(PaymentResponse paymentResponse);
}
