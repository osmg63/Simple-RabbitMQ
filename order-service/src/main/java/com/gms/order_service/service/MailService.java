package com.gms.order_service.service;

import com.gms.order_service.dto.OrderDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    Logger logger = LoggerFactory.getLogger(MailService.class);
    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String myMail;
    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    @RabbitListener(queues = "${rabbitmq.notification.queue}")
    public void sendSimpleEmail(OrderDto orderDto) {
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(myMail);
            message.setTo(orderDto.getUserMail());
            message.setSubject("Sipariş Bilgilendirme ABC Ticaret");
            message.setText(orderDto.getBody());
            mailSender.send(message);
            logger.info("Email sent");
        }catch (Exception e){
            logger.error(e.getMessage()+"gönderilemedi");
        }

    }
}
