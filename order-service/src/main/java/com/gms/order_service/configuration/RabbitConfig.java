package com.gms.order_service.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Value("${rabbitmq.exchange}")
    private String exchangeName;
    
    @Value("${rabbitmq.order.queue}")
    private String orderQueue;

    @Value("${rabbitmq.user.checked.queue}")
    private String userCheckedQueue;

    @Value("${rabbitmq.order.final.queue}")
    private String orderFinalQueue;

    
    @Value("${rabbitmq.product.checked.queue}")
    private String productCheckQueue;


    @Value("${rabbitmq.stock.checked.queue}")
    private String stockCheckQueue;


    @Value("${rabbitmq.notification.queue}")
    private String notificationQueue;





    @Value("${rabbitmq.routing.order}")
    private String orderRoutingKey;

    @Value("${rabbitmq.routing.userChecked}")
    private String userCheckedRoutingKey;

    @Value("${rabbitmq.routing.orderFinal}")
    private String orderFinalRoutingKey;

    @Value("${rabbitmq.routing.productChecked}")
    private String productCheckRoutingKey;
    @Value("${rabbitmq.routing.notification}")
    private String notificationRoutingKey;
    @Value("${rabbitmq.routing.stockChecked}")
    private String stockCheckedRoutingKey;






    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(exchangeName);
    }




    @Bean
    public Queue orderQueue() {
        return new Queue(orderQueue, true);
    }

    @Bean
    public Queue userCheckedQueue() {
        return new Queue(userCheckedQueue, true);
    }

    @Bean
    public Queue orderFinalQueue() {
        return new Queue(orderFinalQueue, true);
    }
    @Bean
    public Queue productCheckQueue() {
        return new Queue(productCheckQueue, true);
    }

    @Bean
    public Queue stockCheckQueue() {
        return new Queue(stockCheckQueue, true);
    }

    @Bean
    public Queue notificationQueue() {
        return new Queue(notificationQueue, true);
    }



    @Bean
    public Binding orderBinding() {
        return BindingBuilder.bind(orderQueue()).to(exchange()).with(orderRoutingKey);
    }

    @Bean
    public Binding userCheckedBinding() {
        return BindingBuilder.bind(userCheckedQueue()).to(exchange()).with(userCheckedRoutingKey);
    }

    @Bean
    public Binding orderFinalBinding() {
        return BindingBuilder.bind(orderFinalQueue()).to(exchange()).with(orderFinalRoutingKey);
    }
    @Bean
    public Binding productCheckBinding() {
        return BindingBuilder.bind(productCheckQueue()).to(exchange()).with(productCheckRoutingKey);
    }

    @Bean
    public Binding stockCheckBinding() {
        return BindingBuilder.bind(stockCheckQueue()).to(exchange()).with(stockCheckedRoutingKey);
    }
    @Bean
    public Binding notificationBinding() {
        return BindingBuilder.bind(notificationQueue()).to(exchange()).with(notificationRoutingKey);
    }



    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
}
