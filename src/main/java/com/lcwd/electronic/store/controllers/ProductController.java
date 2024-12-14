package com.lcwd.electronic.store.controllers;

import com.lcwd.electronic.store.dtos.*;
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

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @Autowired
    private FileService fileService;

    Logger logger = LoggerFactory.getLogger(ProductController.class);
    @Value("${user.product.image.path}")
    private String imageUploadPath;

    //create Product
    @PostMapping
    public ResponseEntity<ApiResponseMessage> createProduct(@RequestBody ProductDto productDto){

        ProductDto dto = service.createProduct(productDto);
        ApiResponseMessage message = ApiResponseMessage.builder()
                .message("User created Successfully")
                .success(true)
                .status(HttpStatus.OK).build();
        return new ResponseEntity<>(message,HttpStatus.OK);

    }

    //update
    @PutMapping("/updateProduct/{productId}")
    public ResponseEntity<ApiResponseMessage> updateProduct(@RequestBody ProductDto productdto,@PathVariable String productId){
        service.updateProduct(productdto,productId);
        ApiResponseMessage message = ApiResponseMessage.builder()
                .message("User updated successfully")
                .success(true)
                .status(HttpStatus.OK).build();
        return new ResponseEntity<>(message,HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponseMessage> deleteProduct(@PathVariable String productId){

        service.deleteProduct(productId);
        ApiResponseMessage message = ApiResponseMessage.builder()
                .message("User deleted successfully")
                .success(true)
                .status(HttpStatus.OK).build();
        return new ResponseEntity<>(message,HttpStatus.OK);
    }

    //get single
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getSingleProduct(@PathVariable String productId){
        System.out.println(productId);
        ProductDto dto = service.getSingleProduct(productId);
        return new ResponseEntity<>(dto,HttpStatus.OK);

    }

//    //get all
    @GetMapping
    public PageableResponse<ProductDto> getAll(
            @RequestParam(value= "pageNumber",defaultValue = "0",required = false) int pageNumber ,
            @RequestParam(value="pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value="sortBy",defaultValue = "title",required = false) String sortBy,
            @RequestParam(value="sortDir",defaultValue = "asc",required = false) String sortDirr)
    {
            PageableResponse<ProductDto> dto = service.getAll(pageNumber,pageSize,sortBy,sortDirr);
            return dto;
    }
    //get all : live
    @GetMapping("/liveProduct")
    public PageableResponse<ProductDto> getAlllive(
            @RequestParam(value= "pageNumber",defaultValue = "0",required = false) int pageNumber ,
            @RequestParam(value="pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value="sortBy",defaultValue = "title",required = false) String sortBy,
            @RequestParam(value="sortDir",defaultValue = "asc",required = false) String sortDirr)
    {
        PageableResponse<ProductDto> dto = service.getAlllive(pageNumber,pageSize,sortBy,sortDirr);
        return dto;
    }

//    //search product
     @GetMapping("/searchByTitle/{subtitle}")
     public PageableResponse<ProductDto> searchByTitle(
             @PathVariable String subtitle,
             @RequestParam(value= "pageNumber",defaultValue = "0",required = false) int pageNumber ,
             @RequestParam(value="pageSize",defaultValue = "10",required = false) int pageSize,
             @RequestParam(value="sortBy",defaultValue = "title",required = false) String sortBy,
             @RequestParam(value="sortDir",defaultValue = "asc",required = false) String sortDirr)
     {
      PageableResponse<ProductDto> dto = service.searchByTitle(subtitle,pageNumber,pageSize,sortBy,sortDirr);
      return dto;
     }

    //upload image
    @PostMapping("/image/{productId}")
    public ResponseEntity<ImageResponse> uploadProductImage(
            @RequestParam("productImage") MultipartFile image,
            @PathVariable String productId
    ){
        String imageName = fileService.uploadImage(image,imageUploadPath);
        System.out.println(imageName);
        ProductDto productDto = service.getSingleProduct(productId);
        productDto.setProductImageName(imageName);
        System.out.println(productDto);
        //userservice.updateUser(user,userid);
        service.updateProduct(productDto,productId);
        ImageResponse imageResponse = ImageResponse.builder().imageName(productDto.getProductImageName()).success(true).status(HttpStatus.OK).build();
        return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
    }

    //serve image
    @GetMapping("/image/{productId}")
    public void serveUserImage(@PathVariable String productId, HttpServletResponse response) throws IOException {
        ProductDto dto = service.getSingleProduct(productId);
        logger.info("User image name :{}",dto.getProductImageName());
        logger.info("----------------------------------------");

        InputStream resource = fileService.getResource(imageUploadPath,dto.getProductImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);

        StreamUtils.copy(resource,response.getOutputStream());

    }

}
