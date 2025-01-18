package com.dalvis.docgen_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DocgenServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DocgenServiceApplication.class, args);
	}

}
