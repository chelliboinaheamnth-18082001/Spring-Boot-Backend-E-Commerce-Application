package main.java.com.example.Notification.Service.Service.OrderReceivedDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemEvent {

    private String productId;
    private Integer quantity;
    private BigDecimal price;
}