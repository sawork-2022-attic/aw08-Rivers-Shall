package com.micropos.order.controller;

import com.micropos.api.OrdersApi;
import com.micropos.cart.mapper.CartMapper;
import com.micropos.cart.mapper.CartMapperImpl;
import com.micropos.cart.model.Cart;
import com.micropos.dto.CartDto;
import com.micropos.dto.OrderDto;
import com.micropos.order.mapper.OrderMapper;
import com.micropos.order.model.Order;
import com.micropos.order.service.OrderService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController implements OrdersApi {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderService orderService;

    @Override
    public ResponseEntity<OrderDto> createOrder(CartDto cartDto) {
        Cart cart = cartMapper.toCart(cartDto);
        return new ResponseEntity<>(orderMapper.toOrderDto(orderService.createOrder(cart)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<OrderDto>> listOrders() {
        List<OrderDto> orderDtos = orderMapper.toOrderDtos(orderService.listOrders());
        return new ResponseEntity<>(orderDtos, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<OrderDto> deliverOrder(Integer orderId) {
        OrderDto orderDto = orderMapper.toOrderDto(orderService.deliverById(orderId));
        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }
}
