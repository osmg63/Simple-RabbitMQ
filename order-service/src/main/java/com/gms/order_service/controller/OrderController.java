package com.gms.order_service.controller;

import com.gms.order_service.dto.OrderDto;
import com.gms.order_service.service.OrderProducer;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class OrderController {
    private final OrderProducer orderProducer;

    public OrderController(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }

    @PostMapping("/orders")
    public String createOrder(@RequestBody OrderDto order) {
        orderProducer.sendOrder(order);
        return "Sipariş kuyruğa gönderildi.";
    }
}
