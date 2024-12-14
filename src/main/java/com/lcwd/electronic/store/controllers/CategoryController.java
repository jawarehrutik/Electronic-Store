package com.lcwd.electronic.store.controllers;

import com.lcwd.electronic.store.dtos.*;
import com.lcwd.electronic.store.services.CategoryService;
import com.lcwd.electronic.store.services.FileService;
import com.lcwd.electronic.store.services.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService service;

    @Autowired
    private ProductService productService;
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private FileService fileService;

    @Value("${user.category.image.path}")
    private String imageUploadPath;

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
          CategoryDto category = service.getById(id);
          if (category != null && category.getCoverImage() != null) {
              // Delete the image from storage
              fileService.deleteImage(imageUploadPath, category.getCoverImage());
          }
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
              @RequestParam(value="sortBy",defaultValue = "title",required = false) String sortBy,
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

      //upload image
      @PostMapping("/image/{id}")
      public ResponseEntity<ImageResponse> uploadUserImage(
              @RequestParam("Image") MultipartFile image,
              @PathVariable String id
      )
      {

          String imageName = fileService.uploadImage(image,imageUploadPath);

          CategoryDto category = service.getById(id);
          category.setCoverImage(imageName);
          service.upadate(category,id);


          ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).success(true).status(HttpStatus.OK).build();
          return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
      }

      //serve image
    @GetMapping("/image/{id}")
    public void serveUserImage(@PathVariable String id, HttpServletResponse response) throws IOException {
        CategoryDto category = service.getById(id);
        logger.info("User image name :{}",category.getCoverImage());
        logger.info("----------------------------------------");

        InputStream resource = fileService.getResource(imageUploadPath,category.getCoverImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);

        StreamUtils.copy(resource,response.getOutputStream());

    }

    //create product with category
    @PostMapping("/{categoryId}/products")
    public ResponseEntity<ProductDto> createProductWithCategory(
            @PathVariable String categoryId,
             @RequestBody ProductDto ProductDto)
    {
        ProductDto dto=productService.createProductWithCategory(ProductDto,categoryId);
        return new ResponseEntity<>(dto,HttpStatus.CREATED);
    }

    //update category of product
    @PutMapping("/{categoryId}/product/{productId}")
    public ResponseEntity<ProductDto> updateCategory(@PathVariable String categoryId,@PathVariable String productId ){

        ProductDto dto = productService.updateCategory(productId,categoryId);

        return new ResponseEntity<>(dto,HttpStatus.OK);
    }



}
