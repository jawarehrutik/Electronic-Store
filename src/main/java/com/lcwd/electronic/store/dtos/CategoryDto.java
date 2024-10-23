package com.lcwd.electronic.store.dtos;


import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {



    private String categoryId;

    @NotBlank
    @Min(value=4,message="title must be minimum 4 character")
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String coverImage;

}
