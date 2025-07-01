package com.gms.order_service.service;

import com.gms.order_service.dto.OrderDto;
import com.gms.order_service.repository.StockRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StockService {
    Logger logger = LoggerFactory.getLogger(StockService.class);
    private final RabbitTemplate rabbitTemplate;
    private final StockRepo stockRepo;
    @Value("${rabbitmq.exchange}")
    private String orderExchange;
    @Value("${rabbitmq.routing.orderFinal}")
    private String finalCheckedRoutingKey;
    @Value("${rabbitmq.routing.notification}")
    private String notificationRoutingKey;



    public StockService(RabbitTemplate rabbitTemplate, StockRepo stockRepo) {
        this.rabbitTemplate = rabbitTemplate;
        this.stockRepo = stockRepo;
    }

    @RabbitListener(queues = "${rabbitmq.stock.checked.queue}")
    public void stockChecked(OrderDto order) {
        var data=stockRepo.findByProduct_Id(order.getProductId());
      if(data==null) {
          logger.warn("stock check fail");
          order.setBody("Siparişiniz Başarısız stok bulunamadı");
          rabbitTemplate.convertAndSend(orderExchange, notificationRoutingKey, order);
          return;
      }
      rabbitTemplate.convertAndSend(orderExchange,finalCheckedRoutingKey,order);
      logger.info("stock check success");
    }




}
