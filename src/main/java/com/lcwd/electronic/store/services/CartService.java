package com.lcwd.electronic.store.services;

import com.lcwd.electronic.store.dtos.AddItemToCartRequest;
import com.lcwd.electronic.store.dtos.CartDto;
import org.apache.coyote.BadRequestException;

public interface CartService {

    // add item to cart
    CartDto addItemtoCart(String userId, AddItemToCartRequest request) throws BadRequestException;

    //delete item from cart
    void removeItemfromCart(String userId,int cartItem);

    //remove all items from cart
    void clearCart(String userId);

    //get cart by userId
    CartDto getCartByUser(String userId);
}
