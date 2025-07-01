package com.gms.order_service.service;

import com.gms.order_service.dto.OrderDto;
import com.gms.order_service.entity.User;
import com.gms.order_service.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RabbitTemplate rabbitTemplate;
    @Value("${rabbitmq.exchange}")
    private String orderExchange;
    @Value("${rabbitmq.routing.productChecked}")
    private String productCheckedRoutingKey;
    @Value("${rabbitmq.routing.notification}")
    private String notificationRoutingKey;




    Logger logger = LoggerFactory.getLogger(UserService.class);


    public UserService(UserRepository userRepository, RabbitTemplate rabbitTemplate) {
        this.userRepository = userRepository;
        this.rabbitTemplate = rabbitTemplate;
    }
    public User save(User user) {
        return userRepository.save(user);
    }
    @RabbitListener(queues = "${rabbitmq.user.checked.queue}")
    public void findById(OrderDto orderDto) {

        var data= userRepository.findById(orderDto.getUserId());
        if(data.isPresent()) {
            logger.info("pass user check order"+orderDto.getProductId());
            orderDto.setUserMail(data.get().getEmail());
            rabbitTemplate.convertAndSend(orderExchange,productCheckedRoutingKey,orderDto);
            return;
        }
        orderDto.setBody("Siparişiniz Başarısız üye kaydı gerekmektedir..");

        rabbitTemplate.convertAndSend(orderExchange, notificationRoutingKey, orderDto);
        logger.info("not pass user check order"+orderDto.getProductId());
    }



}
