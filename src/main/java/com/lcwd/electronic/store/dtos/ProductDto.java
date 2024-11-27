package com.lcwd.electronic.store.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {


    private String productId;

    private String title;

    private String description;

    private int price;
    private int discountPrice;

    private int quantity;

    private Date addedData;

    private boolean live;

    private boolean stock;

    private String productImageName;

    public boolean getLive() {
        return live;
    }

    public boolean getStock() {
        return stock;
    }
}
