package com.micropos.order;

import com.micropos.cart.mapper.CartMapper;
import com.micropos.cart.mapper.CartMapperImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@EnableDiscoveryClient
@SpringBootApplication
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

    @Bean
    public CartMapper cartMapper() {
        return new CartMapperImpl();
    }
}
