package com.dalvis.product_quote_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {
    
    @GetMapping("/test")
    public String Test(){
        return "Test Successful..!!";
    }
}
