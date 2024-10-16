package com.lcwd.electronic.store.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

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


}
