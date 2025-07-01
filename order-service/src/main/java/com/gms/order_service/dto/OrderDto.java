package com.gms.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private int orderId;
    private int userId;
    private int productId;
    private String userName;
    private String userMail;
    private String productName;
    private String body;
    private String header;

}
