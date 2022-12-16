package com.food.ordering.system.order.service.messaging.listener.kafka;

import com.food.ordering.system.kafka.order.avro.model.PaymentRequestAvroModel;
import com.food.ordering.system.kafka.producer.service.KafkaProducer;
import com.food.ordering.system.order.service.domain.config.OrderServiceConfigData;
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.food.ordering.system.order.service.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import com.food.ordering.system.order.service.messaging.mapper.OrderMessagingDataMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

@RequiredArgsConstructor
@Slf4j
@Component
public class CreateOrderKafkaMessagePublisher implements OrderCreatedPaymentRequestMessagePublisher {

  private final OrderMessagingDataMapper orderMessagingDataMapper;
  private final OrderServiceConfigData orderServiceConfigData;
  private final KafkaProducer<String, PaymentRequestAvroModel> kafkaProducer;

  @Override
  public void publish(OrderCreatedEvent domainEvent) {
    String orderId = domainEvent.getOrder().getId().getValue().toString();
    log.info("Received OrderCreatedEvent for order id: {}", orderId);

    PaymentRequestAvroModel paymentRequestAvroModel = orderMessagingDataMapper.orderCreatedEventToPaymentRequestAvroModel(domainEvent);
    kafkaProducer.send(orderServiceConfigData.getPaymentRequestTopicName(), orderId, paymentRequestAvroModel,
        getKafkaCallback(orderServiceConfigData.getPaymentResponseTopicName(), paymentRequestAvroModel));
  }

  private ListenableFutureCallback<SendResult<String, PaymentRequestAvroModel>> getKafkaCallback(String paymentResponseTopicName,
      PaymentRequestAvroModel paymentRequestAvroModel) {
    return new ListenableFutureCallback<SendResult<String, PaymentRequestAvroModel>>() {
      @Override
      public void onFailure(Throwable ex) {
        log.error("Error while sending paymentRequestAvroModel");
      }

      @Override
      public void onSuccess(SendResult<String, PaymentRequestAvroModel> result) {

      }
    };
  }
}
