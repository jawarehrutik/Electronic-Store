package com.lcwd.electronic.store.services;

import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.ProductDto;
import com.lcwd.electronic.store.entities.Category;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    //create
    public ProductDto createProduct(ProductDto productDto);

    //update
    public ProductDto updateProduct(ProductDto product,String productId);

    //delete
    public void deleteProduct(String productId);

    //get single
    public ProductDto getSingleProduct(String productId);

    //get all
    public PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir);

    //get all : live
    public PageableResponse<ProductDto> getAlllive(int pageNumber, int pageSize, String sortBy, String sortDir);

    //search product
    public PageableResponse<ProductDto> searchByTitle(String subTitle,int pageNumber, int pageSize, String sortBy, String sortDir);

    //create product with category
    public ProductDto createProductWithCategory(ProductDto dto, String categoryId);

}
