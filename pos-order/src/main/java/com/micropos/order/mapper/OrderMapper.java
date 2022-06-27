package com.micropos.order.mapper;

import com.micropos.order.model.Order;
import com.micropos.order.model.Item;
import com.micropos.dto.OrderDto;
import com.micropos.dto.ItemDto;
import com.micropos.dto.ProductDto;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface OrderMapper {
    List<OrderDto> toOrderDtos(List<Order> orders);

    default Order toOrder(OrderDto orderDto) {
        Order order = new Order();
        order.id(orderDto.getId())
                .items(toItems(orderDto.getItems(), order))
                .status(orderDto.getStatus());
        return order;
    }

    default OrderDto toOrderDto(Order order) {
        return new OrderDto().id(order.id())
                .items(toItemDtos(order.items()))
                .status(order.status());
    }


    default List<ItemDto> toItemDtos(List<Item> items) {
        if (items == null || items.isEmpty()) {
            return null;
        }
        List<ItemDto> list = new ArrayList<>(items.size());
        for (Item item : items) {
            list.add(toItemDto(item));
        }

        return list;
    }

    default List<Item> toItems(List<ItemDto> itemDtos, Order order) {
        if (itemDtos == null || itemDtos.isEmpty()) {
            return null;
        }
        List<Item> list = new ArrayList<>(itemDtos.size());
        for (ItemDto itemDto : itemDtos) {
            list.add(toItem(itemDto, order));
        }

        return list;
    }

    default ItemDto toItemDto(Item item) {
        return new ItemDto().id(item.id())
                .amount(item.quantity())
                .product(getProductDto(item));
    }

    default Item toItem(ItemDto itemDto, Order order) {
        return new Item().id(itemDto.getId())
                .order(order)
                .productId(itemDto.getProduct().getId())
                .productName(itemDto.getProduct().getName())
                .quantity(itemDto.getAmount())
                .unitPrice(itemDto.getProduct().getPrice());
    }

    default ProductDto getProductDto(Item item) {
        return new ProductDto().id(item.productId())
                .name(item.productName())
                .price(item.unitPrice());
    }

}
