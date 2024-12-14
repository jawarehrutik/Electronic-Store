package com.lcwd.electronic.store.services.impl;

import com.lcwd.electronic.store.dtos.AddItemToCartRequest;
import com.lcwd.electronic.store.dtos.CartDto;
import com.lcwd.electronic.store.entities.Cart;
import com.lcwd.electronic.store.entities.CartItem;
import com.lcwd.electronic.store.entities.Product;
import com.lcwd.electronic.store.entities.User;
import com.lcwd.electronic.store.exceptions.ResourceNotFoundException;
import com.lcwd.electronic.store.repositories.CartItemRepository;
import com.lcwd.electronic.store.repositories.CartRepository;
import com.lcwd.electronic.store.repositories.ProductRepository;
import com.lcwd.electronic.store.repositories.UserRepository;
import com.lcwd.electronic.store.services.CartService;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class CartServiceImpl implements CartService {

    //user repo
    @Autowired
    private UserRepository userRepo;

    //product repository
    @Autowired
    private ProductRepository productRepo;

    //cart Repository
    @Autowired
    private CartRepository cartRepo;

    //CartItemRepo
    @Autowired
    private CartItemRepository cartItemRepo;

    //modelmapper
    @Autowired
    private ModelMapper mapper;

    //add item to cart
    @Override
    public CartDto addItemtoCart(String userId, AddItemToCartRequest request) throws BadRequestException {
        int quantity = request.getQuantity();
        String  productId = request.getProductId();

        if(quantity<=0)
        {
            throw new BadRequestException("Request quantity is not valid");
        }
        //fetch product
        Product product = productRepo.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product Not found"));

        //fetch user
        User user = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found"));

        //check whether user have cart or not
        Cart cart=null;
        try{
            cart = cartRepo.findByUser(user).get();
        }
        catch (NoSuchElementException ex)
        {
            //if cart is not present create new cart
            cart = new Cart();
            cart.setCreatedAt(new Date());
        }

        //perform cart operation
        //if cart item already present then update
        AtomicBoolean updated = new AtomicBoolean(false);
        List<CartItem> items = cart.getItems();
        List<CartItem> updatedItem = items.stream().map(item->{
            if(item.getProduct().getProductId().equals(productId)){
                item.setQuantity(quantity);
                item.setTotalPrice(quantity*product.getPrice());
                updated.set(true);
            }
            return item;

        }).collect(Collectors.toList());

        cart.setItems(updatedItem);

        //create items
        if(updated.get()==false) {

            CartItem cartItem = CartItem.builder().quantity(quantity).
                    totalPrice(quantity * product.getPrice()).
                    cart(cart).product(product).build();
            //update cartItems
            cart.getItems().add(cartItem);
        }

        cart.setUser(user);

        Cart upadatedcart = cartRepo.save(cart);

        return mapper.map(upadatedcart,CartDto.class) ;
    }

    @Override
    public void removeItemfromCart(String userId, int cartItem) {
        CartItem cartitem = cartItemRepo.findById(cartItem).orElseThrow(()-> new ResourceNotFoundException("cart item not found"));
        cartItemRepo.delete(cartitem);

    }

    @Override
    public void clearCart(String userId) {
        User user = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found"));
        Cart cart = cartRepo.findByUser(user).orElseThrow(()->new ResourceNotFoundException("Cart not found"));
        cart.getItems().clear();
        cartRepo.save(cart);

    }

    @Override
    public CartDto getCartByUser(String userId) {
        User user = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found"));
        Cart cart = cartRepo.findByUser(user).orElseThrow(()->new ResourceNotFoundException("Cart not found"));

        return mapper.map(cart,CartDto.class);
    }


}
