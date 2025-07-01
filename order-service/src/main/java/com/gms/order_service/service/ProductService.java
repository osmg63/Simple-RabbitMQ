package com.gms.order_service.service;

import com.gms.order_service.dto.OrderDto;
import com.gms.order_service.repository.ProductRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
     Logger logger = LoggerFactory.getLogger(ProductService.class);
    private final RabbitTemplate rabbitTemplate;
    private final ProductRepo productRepo;
    @Value("${rabbitmq.exchange}")
    private String orderExchange;
    @Value("${rabbitmq.routing.stockChecked}")
    private String stockCheckedRoutingKey;
    @Value("${rabbitmq.routing.notification}")
    private String notificationRoutingKey;


    public ProductService(RabbitTemplate rabbitTemplate, ProductRepo productRepo) {
        this.rabbitTemplate = rabbitTemplate;
        this.productRepo = productRepo;
    }
    @RabbitListener(queues = "${rabbitmq.product.checked.queue}")
    public void getProduct(OrderDto order) {
       var data=productRepo.getProductById(order.getProductId());
        if(data==null) {
            logger.info("Dont pass order by product: {}", order);
            order.setBody("Siparişiniz Başarısız ürün bulunamadı");

            rabbitTemplate.convertAndSend(orderExchange, notificationRoutingKey, order);
            return;
        }
        logger.info("pass  order by product: {}", order);
        order.setProductName(data.getName());
        rabbitTemplate.convertAndSend(orderExchange,stockCheckedRoutingKey,order);
    }











}



