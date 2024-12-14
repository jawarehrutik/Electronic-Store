package com.lcwd.electronic.store.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name="products")
public class Product {


        @Id
        private String productId;
        private String title;

        @Column(length = 10000)
        private String description;

        private int price;
        private int discountPrice;

        private int quantity;

        private Date addedData;

        private boolean live;

        private boolean stock;

        private String productImageName;

        @ManyToOne
        @JoinColumn(name="category_id")
        private Category category;

        @jakarta.persistence.PrePersist
        protected void onCreate() {
                this.productId = UUID.randomUUID().toString();
                this.addedData = new Date();
        }

}

