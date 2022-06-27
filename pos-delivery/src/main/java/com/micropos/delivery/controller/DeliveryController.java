package com.micropos.delivery.controller;

import com.micropos.api.DeliverApi;
import com.micropos.dto.OrderDto;
import com.micropos.order.mapper.OrderMapper;
import com.micropos.order.model.Order;
import com.micropos.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
@EnableJpaRepositories({"com.micropos.order.repository"})
@ComponentScan("com.micropos.order.mapper")
@EntityScan("com.micropos.order.model")
public class DeliveryController implements DeliverApi {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public ResponseEntity<OrderDto> deliveryStatus(Integer orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        Order order = orderOptional.orElse(null);
        if (order == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        order.status(OrderDto.StatusEnum.DELIVERED);
        orderRepository.save(order);
        return new ResponseEntity<>(orderMapper.toOrderDto(order), HttpStatus.OK);
    }
}
