package com.lcwd.electronic.store.services.impl;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.ProductDto;
import com.lcwd.electronic.store.dtos.UserDto;
import com.lcwd.electronic.store.entities.Category;
import com.lcwd.electronic.store.entities.Product;
import com.lcwd.electronic.store.entities.User;
import com.lcwd.electronic.store.exceptions.ResourceNotFoundException;
import com.lcwd.electronic.store.helper.Helper;
import com.lcwd.electronic.store.repositories.CategoryRepository;
import com.lcwd.electronic.store.repositories.ProductRepository;
import com.lcwd.electronic.store.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productrepo;

    @Autowired
    private CategoryRepository categoryrepo;
    @Autowired
    private ModelMapper mapper;

    @Override
    public ProductDto createProduct(ProductDto productDto) {

        Product product = dtoToEntity(productDto);
        Product saveProduct =  productrepo.save(product);
        ProductDto newProduct = entityToDto(saveProduct);
        return newProduct;
    }

    @Override
    public ProductDto updateProduct(ProductDto productdto, String productId) {

        Product storedprod = productrepo.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product not foung"));
        storedprod.setTitle(productdto.getTitle());
        storedprod.setDescription(productdto.getDescription());
        storedprod.setPrice(productdto.getPrice());
        storedprod.setDiscountPrice(productdto.getDiscountPrice());
        storedprod.setQuantity(productdto.getQuantity());
        storedprod.setAddedData(productdto.getAddedData());
        storedprod.setLive(productdto.getLive());
        storedprod.setStock(productdto.getStock());
        storedprod.setProductImageName(productdto.getProductImageName());

        Product p =productrepo.save(storedprod);

        ProductDto dto = entityToDto(p);

        return dto;
    }

    @Override
    public void deleteProduct(String productId) {
        productrepo.deleteById(productId);
    }

    @Override
    public ProductDto getSingleProduct(String productId) {
        System.out.println(productId);
        Product product = productrepo.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product not found"));
        ProductDto dto = entityToDto(product);
        return dto;
    }

    @Override
    public PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("dsc")?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending()));
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productrepo.findAll(pageable);
        PageableResponse<ProductDto> response= Helper.getPageableResponse(page,ProductDto.class);
        return response;

    }

    @Override
    public PageableResponse<ProductDto> getAlllive(int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("dsc")?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending()));
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productrepo.findByLiveTrue(pageable);
        PageableResponse<ProductDto> response= Helper.getPageableResponse(page,ProductDto.class);
        return response;
    }

    @Override
    public PageableResponse<ProductDto> searchByTitle(String subTitle,int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("dsc")?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending()));
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productrepo.findByTitleContaining(subTitle,pageable);
        PageableResponse<ProductDto> response= Helper.getPageableResponse(page,ProductDto.class);
        return response;
    }

    //create product with category
    @Override
    public ProductDto createProductWithCategory(ProductDto dto, String categoryId) {

        Category category = categoryrepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category not found"));

        Product product = dtoToEntity(dto);

        product.setCategory(category);

        Product saveProduct =  productrepo.save(product);
        ProductDto newProduct = entityToDto(saveProduct);
        return newProduct;
    }


    private Product dtoToEntity(ProductDto productDto)
    {
        return mapper.map(productDto,Product.class);
    }

    private ProductDto entityToDto(Product product)
    {
        return mapper.map(product,ProductDto.class);
    }



}
