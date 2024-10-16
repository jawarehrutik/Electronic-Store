package com.lcwd.electronic.store.controllers;

import com.lcwd.electronic.store.dtos.ApiResponseMessage;
import com.lcwd.electronic.store.dtos.CategoryDto;
import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.UserDto;
import com.lcwd.electronic.store.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService service;
    //create
      @PostMapping
      public ResponseEntity<CategoryDto> create(@RequestBody CategoryDto category){
          CategoryDto dto = service.create(category);

          return new ResponseEntity<>(dto, HttpStatus.CREATED);
      }

//    //update
      @PostMapping("update/{id}")
      public ResponseEntity<CategoryDto> upadate(@RequestBody CategoryDto categoryDto,@PathVariable String id) {
          CategoryDto dto = service.upadate(categoryDto,id);
          return new ResponseEntity<>(dto,HttpStatus.OK);
      }
//
//    //delete
      @DeleteMapping("/{id}")
      public ResponseEntity<ApiResponseMessage> delete(@PathVariable String id)
      {
          service.delete(id);
          ApiResponseMessage message = ApiResponseMessage.builder()
                  .message("User deleted successfully")
                  .success(true)
                  .status(HttpStatus.OK).build();
          return new ResponseEntity<>(message,HttpStatus.OK);
      }
//
//    //get all
      @GetMapping
      public ResponseEntity<PageableResponse<CategoryDto>> getAll(
              @RequestParam(value= "pageNumber",defaultValue = "0",required = false) int pageNumber ,
              @RequestParam(value="pageSize",defaultValue = "10",required = false) int pageSize,
              @RequestParam(value="sortBy",defaultValue = "name",required = false) String sortBy,
              @RequestParam(value="sortDir",defaultValue = "asc",required = false) String sortDir
      )
      {
          PageableResponse<CategoryDto> response = service.getAll(pageNumber,pageSize,sortBy,sortDir);
          return new ResponseEntity<>(response,HttpStatus.OK);
      }
//
//    //getsingle category details
      @GetMapping("/{id}")
      public CategoryDto getById(@PathVariable String id)
      {
            CategoryDto categoryDto=service.getById(id);
            return categoryDto;
      }

}
