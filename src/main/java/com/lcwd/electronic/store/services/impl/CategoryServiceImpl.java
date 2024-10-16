package com.lcwd.electronic.store.services.impl;

import com.lcwd.electronic.store.dtos.CategoryDto;
import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.UserDto;
import com.lcwd.electronic.store.entities.Category;
import com.lcwd.electronic.store.entities.User;
import com.lcwd.electronic.store.exceptions.ResourceNotFoundException;
import com.lcwd.electronic.store.helper.Helper;
import com.lcwd.electronic.store.repositories.CategoryRepository;
import com.lcwd.electronic.store.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository repo;
    @Autowired
    private ModelMapper mapper;

    @Override
    public CategoryDto create(CategoryDto categorydto) {
        Category category = mapper.map(categorydto,Category.class);
        Category newcategory = repo.save(category);
        CategoryDto newdto = mapper.map(newcategory,CategoryDto.class);
        return newdto;
    }

    @Override
    public CategoryDto upadate(CategoryDto categoryDto, String id) {

        Category category = repo.findById(id).orElseThrow(()->new ResourceNotFoundException("Category Not found"));
        category.setDescription(categoryDto.getDescription());
        category.setTitle(categoryDto.getTitle());
        category.setCoverImage(categoryDto.getCoverImage());

        CategoryDto dto = mapper.map(category,CategoryDto.class);

        return dto;
    }

    @Override
    public void delete(String id) {
        repo.deleteById(id);
    }

    @Override
    public PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir)
    {
        Sort sort = (sortDir.equalsIgnoreCase("dsc")?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending()));
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Category> page = repo.findAll(pageable);

        PageableResponse<CategoryDto> response= Helper.getPageableResponse(page,CategoryDto.class);
        return response;
    }

    @Override
    public CategoryDto getById(String id) {
        return mapper.map(repo.findById(id),CategoryDto.class);
    }
}
