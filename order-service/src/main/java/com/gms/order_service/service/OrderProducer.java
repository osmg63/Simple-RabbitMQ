package com.gms.order_service.service;

import com.gms.order_service.dto.OrderDto;
import com.gms.order_service.entity.Order;
import com.gms.order_service.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {
    private final RabbitTemplate rabbitTemplate;
    private final OrderRepository orderRepository;
    @Value("${rabbitmq.routing.order}")
    private String orderQueue;
    @Value("${rabbitmq.routing.userChecked}")
    private String userCheckedRoutingKey;
    @Value("${rabbitmq.exchange}")
    private String orderExchange;
    @Value("${rabbitmq.routing.notification}")
    private String notificationRoutingKey;

    Logger logger = LoggerFactory.getLogger(OrderProducer.class);

    public OrderProducer(RabbitTemplate rabbitTemplate,  OrderRepository orderRepository) {
        this.rabbitTemplate = rabbitTemplate;
        this.orderRepository = orderRepository;
    }
    public void sendOrder(OrderDto order) {
        rabbitTemplate.convertAndSend(orderExchange, userCheckedRoutingKey, order);
        logger.info("Sent order: {}", order);
    }
    @RabbitListener(queues = "${rabbitmq.order.final.queue}")
    public void catchOrder(OrderDto order) {
        ModelMapper modelMapper = new ModelMapper();
        var data=modelMapper.map(order, Order.class);
        orderRepository.save(data);
        logger.info("kayıt yapıldı: "+order.getOrderId());
        order.setBody(order.getProductName()+" : Siparişiniz Alındı");
        order.setHeader("Sipariş Bilgilendirme ABC Ticaret");

        rabbitTemplate.convertAndSend(orderExchange, notificationRoutingKey, order);
    }
}
