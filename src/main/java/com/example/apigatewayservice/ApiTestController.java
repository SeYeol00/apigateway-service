package com.example.apigatewayservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/")
public class ApiTestController {

    @GetMapping("/welcome")
    public String welcome(){
        log.info("Welcome to the Api Gateway");
        return "Welcome to the Api Gateway";
    }
}
