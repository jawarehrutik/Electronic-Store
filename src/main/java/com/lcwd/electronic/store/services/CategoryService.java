package com.lcwd.electronic.store.services;

import com.lcwd.electronic.store.dtos.ApiResponseMessage;
import com.lcwd.electronic.store.dtos.CategoryDto;
import com.lcwd.electronic.store.dtos.PageableResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface CategoryService {

    //create
    CategoryDto create(CategoryDto category);

    //update
    CategoryDto upadate(CategoryDto categoryDto,String id);

    //delete

    public void delete(String id);


    //get all
    PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir);

    //getsingle category details
    CategoryDto getById(String id);

    //search


}
