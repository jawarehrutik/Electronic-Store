package com.lcwd.electronic.store.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name="cart")
public class Cart {

    @Id
    private String cartId;
    private Date createdAt;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;


    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<CartItem> items = new ArrayList<>();

    @jakarta.persistence.PrePersist
    protected void onCreate() {
        this.cartId = UUID.randomUUID().toString();
    }


}
