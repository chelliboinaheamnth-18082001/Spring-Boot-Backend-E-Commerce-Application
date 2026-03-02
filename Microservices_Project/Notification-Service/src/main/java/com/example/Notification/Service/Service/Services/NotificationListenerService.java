package com.example.Notification.Service.Service.Services;


import main.java.com.example.Notification.Service.Service.OrderReceivedDTO.OrderReceivedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class NotificationListenerService {

    @Bean
    public Consumer<OrderReceivedEvent> orderNotification() {
        return event -> {
            System.out.println("📦 Order Received Notification");
            System.out.println("Order ID     : " + event.getOrderId());
            System.out.println("User ID      : " + event.getUserId());
            System.out.println("Status       : " + event.getOrderStatus());
            System.out.println("Total Amount : " + event.getTotalAmount());
            System.out.println("Created At   : " + event.getCreatedAt());

            System.out.println("Items:");
            event.getItems().forEach(item ->
                    System.out.println(
                            " - ProductId: " + item.getProductId() +
                                    ", Qty: " + item.getQuantity() +
                                    ", Price: " + item.getPrice()
                    )
            );

            System.out.println("✅ Notification processed successfully\n");
        };
    }
}


