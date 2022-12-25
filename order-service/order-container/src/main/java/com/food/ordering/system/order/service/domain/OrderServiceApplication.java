package com.food.ordering.system.order.service.domain;

import com.food.ordering.system.order.service.domain.ports.input.service.OrderApplicationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.food.ordering.system.order.service.dataacess")
@EntityScan(basePackages = "com.food.ordering.system.order.service.dataacess")
@SpringBootApplication(scanBasePackages = "com.food.ordering.system")
public class OrderServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(OrderApplicationService.class, args);
  }
}
