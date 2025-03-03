package com.shop_hub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.shop_hub.model")
public class ShopHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopHubApplication.class, args);
	}

}
