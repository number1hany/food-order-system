package com.food.ordering.system.order.service.messaging.publisher.kafka;

import com.food.ordering.system.kafka.order.avro.model.RestaurantApprovalRequestAvroModel;
import com.food.ordering.system.kafka.producer.service.KafkaProducer;
import com.food.ordering.system.order.service.domain.config.OrderServiceConfigData;
import com.food.ordering.system.order.service.domain.event.OrderPaidEvent;
import com.food.ordering.system.order.service.domain.ports.output.message.publisher.restaurant.OrderPaidRestaurantRequestMessagePublisher;
import com.food.ordering.system.order.service.messaging.mapper.OrderMessagingDataMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class PayOrderKafkaMessagePublisher implements OrderPaidRestaurantRequestMessagePublisher {

  private final OrderMessagingDataMapper orderMessagingDataMapper;
  private final OrderServiceConfigData orderServiceConfigData;
  private final KafkaProducer<String, RestaurantApprovalRequestAvroModel> kafkaProducer;
  private final OrderKafkaMessageHelper orderKafkaMessageHelper;

  @Override
  public void publish(OrderPaidEvent domainEvent) {
    String orderId = domainEvent.getOrder().getId().getValue().toString();

    try {
      RestaurantApprovalRequestAvroModel restaurantApprovalRequestAvroModel = orderMessagingDataMapper.orderPaidEventToRestaurantRequestAvroModel(domainEvent);
      kafkaProducer.send(orderServiceConfigData.getRestaurantApprovalRequestTopicName(),
          orderId,
          restaurantApprovalRequestAvroModel,
          orderKafkaMessageHelper.getKafkaCallback(orderServiceConfigData.getPaymentResponseTopicName(), restaurantApprovalRequestAvroModel, orderId,
              "RestaurantApprovalRequestAvroModel")
      );
      log.info("RestaurantApprovalRequestAvroModel send to kafka for order id: {}", orderId);
    } catch (Exception e) {
      log.error("Error while sending RestaurantApprovalRequestAvroModel message" +
          "to kafka with order id: {}, error: {}", orderId, e.getMessage());
    }

  }

}
