package com.micropos.counter.service;

import com.micropos.dto.CartDto;
import com.micropos.dto.ItemDto;
import org.springframework.stereotype.Service;

@Service
public class CounterService {

    public double getTotal(CartDto cart) {
        double total = 0;
        for (ItemDto item : cart.getItems()) {
            total += item.getAmount() * item.getProduct().getPrice();
        }
        return total;
    }
}
