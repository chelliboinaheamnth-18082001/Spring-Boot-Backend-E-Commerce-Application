package com.example.user_service.Controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class SpringCloudBusController {


    @Value("${app.message}")
    private String message;

    @GetMapping("/messge")
    public String getMessage()
    {
        return message;
    }

}
