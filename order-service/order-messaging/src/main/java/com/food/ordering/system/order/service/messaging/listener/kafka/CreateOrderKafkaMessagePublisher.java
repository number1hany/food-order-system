package com.food.ordering.system.order.service.messaging.listener.kafka;

import com.food.ordering.system.kafka.order.avro.model.PaymentRequestAvroModel;
import com.food.ordering.system.kafka.producer.service.KafkaProducer;
import com.food.ordering.system.order.service.domain.config.OrderServiceConfigData;
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.food.ordering.system.order.service.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import com.food.ordering.system.order.service.messaging.mapper.OrderMessagingDataMapper;
import com.food.ordering.system.order.service.messaging.publisher.kafka.OrderKafkaMessageHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
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
  private final OrderKafkaMessageHelper orderKafkaMessageHelper;

  @Override
  public void publish(OrderCreatedEvent domainEvent) {
    String orderId = domainEvent.getOrder().getId().getValue().toString();
    log.info("Received OrderCreatedEvent for order id: {}", orderId);

    try {
      PaymentRequestAvroModel paymentRequestAvroModel = orderMessagingDataMapper.orderCreatedEventToPaymentRequestAvroModel(domainEvent);
      kafkaProducer.send(orderServiceConfigData.getPaymentRequestTopicName(), orderId, paymentRequestAvroModel,
          orderKafkaMessageHelper.getKafkaCallback(orderServiceConfigData.getPaymentResponseTopicName(), paymentRequestAvroModel));

      log.info("PaymentRequestAvroModel send to kafka order id: {}",
          paymentRequestAvroModel.getOrderId());
    } catch (Exception e) {
      log.error("Error while sending PaymentRequestAvroModel" +
          "to kafka with order id: {}, error: {}", orderId, e.getMessage());
    }

  }

  private ListenableFutureCallback<SendResult<String, PaymentRequestAvroModel>> getKafkaCallback(String paymentResponseTopicName,
      PaymentRequestAvroModel paymentRequestAvroModel) {
    return new ListenableFutureCallback<SendResult<String, PaymentRequestAvroModel>>() {
      @Override
      public void onFailure(Throwable ex) {
        log.error("Error while sending paymentRequestAvroModel" +
            "message {} to topic {}", paymentRequestAvroModel.toString(), paymentResponseTopicName, ex);
      }

      @Override
      public void onSuccess(SendResult<String, PaymentRequestAvroModel> result) {
        RecordMetadata metadata = result.getRecordMetadata();
        log.info("Received successful response from Kafka for order id: {}" +
                "Topic: {} Partition: {} Offset: {} Timestamp: {}",
            paymentRequestAvroModel.getOrderId(),
            metadata.topic(),
            metadata.partition(),
            metadata.offset(),
            metadata.timestamp());
      }
    };
  }
}
