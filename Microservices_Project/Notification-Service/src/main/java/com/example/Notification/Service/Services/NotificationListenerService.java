package com.example.Notification.Service.Services;


import com.example.Notification.Service.OrderReceivedDTO.OrderReceivedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationListenerService {

    @RabbitListener(queues = "${RabbitMq.queue.name}")
    public void orderReceivedNotification(OrderReceivedEvent event) {

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
    }
}