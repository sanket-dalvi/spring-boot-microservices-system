package com.dalvis.product_quote_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ProductQuoteServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductQuoteServiceApplication.class, args);
	}

}
