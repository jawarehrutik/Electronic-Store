package com.lcwd.electronic.store.controllers;

import com.lcwd.electronic.store.dtos.AddItemToCartRequest;
import com.lcwd.electronic.store.dtos.ApiResponseMessage;
import com.lcwd.electronic.store.dtos.CartDto;
import com.lcwd.electronic.store.services.CartService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartController {
    @Autowired
    private CartService cartService;

    // add item to cart
    @PostMapping("/{userId}")
    public ResponseEntity<CartDto> addItemtoCart(@PathVariable String userId,@RequestBody AddItemToCartRequest request) throws BadRequestException {
        CartDto cartdto = cartService.addItemtoCart(userId,request);
        return new ResponseEntity<>(cartdto, HttpStatus.CREATED);
    }

    //delete item from cart
    @DeleteMapping("/{userId}/items/{cartItem}")
    public ResponseEntity<ApiResponseMessage> removeItemfromCart(@PathVariable String userId, @PathVariable int cartItem){
        cartService.removeItemfromCart(userId,cartItem);
        ApiResponseMessage response = ApiResponseMessage.builder().message("Deleted successfully").success(true).status(HttpStatus.OK).build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    //remove all items from cart
    public ResponseEntity<ApiResponseMessage> clearCart(String userId){
        cartService.clearCart(userId);
        ApiResponseMessage response = ApiResponseMessage.builder().
                message("Cart clear successfully").
                success(true).
                status(HttpStatus.OK).
                build();
        return new ResponseEntity<>(response,HttpStatus.OK);

    }

    //get cart by userId
    public ResponseEntity<CartDto> getCartByUser(String userId){
        CartDto cartdto = cartService.getCartByUser(userId);

        return new ResponseEntity<>(cartdto,HttpStatus.OK);
    }

}
