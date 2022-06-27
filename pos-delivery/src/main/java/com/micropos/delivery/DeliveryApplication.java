package com.micropos.delivery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.web.client.RestTemplate;

import java.util.function.Consumer;

@SpringBootApplication
public class DeliveryApplication {

    private String deliverUrl(Integer orderId) {
        return "http://POS-ORDER-SERVICE/orders/" + orderId + "/deliver";
    }

    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;

    public static void main(String[] args) {
        SpringApplication.run(DeliveryApplication.class, args);
    }

    @Bean
    public Consumer<Integer> orderIdConsumer() {
        return (orderId -> {
            restTemplate.getForObject(deliverUrl(orderId), Order.class);
        });
    }
}
