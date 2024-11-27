package com.lcwd.electronic.store.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(name="categories")
public class Category {

    @Id
    @Column(name="id")
    private String categoryId;

    @Column(name="category_title",length = 60,nullable = false)
    private String title;

    @Column(name="category_description",length=500)
    private String description;

    @Column(name="category_coverimage")
    private String coverImage;

    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();

    @jakarta.persistence.PrePersist
    protected void onCreate() {
        this.categoryId = UUID.randomUUID().toString();
    }


}
