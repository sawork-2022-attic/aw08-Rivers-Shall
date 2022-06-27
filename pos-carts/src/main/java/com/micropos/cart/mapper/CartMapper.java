package com.micropos.cart.mapper;

import com.micropos.cart.model.Cart;
import com.micropos.cart.model.Item;
import com.micropos.dto.CartDto;
import com.micropos.dto.ItemDto;
import com.micropos.dto.ProductDto;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Mapper
public interface CartMapper {
    List<CartDto> toCartDtos(List<Cart> carts);

    Collection<Cart> toCarts(Collection<CartDto> cartDtos);

    default Cart toCart(CartDto cartDto) {
        Cart cart = new Cart();
        cart.id(cartDto.getId())
                .items(toItems(cartDto.getItems(), cart));
        return cart;
    }

    default CartDto toCartDto(Cart cart) {
        return new CartDto().id(cart.id())
                .items(toItemDtos(cart.items()));
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

    default List<Item> toItems(List<ItemDto> itemDtos, Cart cart) {
        if (itemDtos == null || itemDtos.isEmpty()) {
            return null;
        }
        List<Item> list = new ArrayList<>(itemDtos.size());
        for (ItemDto itemDto : itemDtos) {
            list.add(toItem(itemDto, cart));
        }

        return list;
    }

    default ItemDto toItemDto(Item item) {
        return new ItemDto().id(item.id())
                .amount(item.quantity())
                .product(getProductDto(item));
    }

    default Item toItem(ItemDto itemDto, Cart cart) {
        return new Item().id(itemDto.getId())
                .cart(cart)
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
