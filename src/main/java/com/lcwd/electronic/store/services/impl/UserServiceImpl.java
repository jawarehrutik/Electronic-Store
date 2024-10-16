package com.lcwd.electronic.store.services.impl;

import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.UserDto;
import com.lcwd.electronic.store.entities.User;
import com.lcwd.electronic.store.exceptions.ResourceNotFoundException;
import com.lcwd.electronic.store.helper.Helper;
import com.lcwd.electronic.store.repositories.UserRepository;
import com.lcwd.electronic.store.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    private ModelMapper mapper;
    @Override
    public UserDto createUser(UserDto userdto) {

        User user = dtoToEntity(userdto);

        User saveUser = userRepo.save(user);

        UserDto newDto = entityToDto(saveUser);

        return newDto;
    }

    @Override
    public UserDto updateUser(UserDto userdto, String userId) {

        User user =  userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found"));

        user.setName(userdto.getName());
        user.setAbout(userdto.getAbout());
        user.setPassword(userdto.getPassword());
        user.setGender(userdto.getGender());
        user.setImageName(userdto.getImageName());
        userRepo.save(user);
        UserDto newuserdto = entityToDto(user);

        return newuserdto;
    }


    @Override
    public PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("dsc")?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending()));
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<User> page = userRepo.findAll(pageable);

//        List<User> users = page.getContent();
//
//       List<UserDto> userdto=  users.stream().map(user->entityToDto(user)).collect(Collectors.toList());
//
//       PageableResponse<UserDto> response = new PageableResponse<>();
//       response.setContent(userdto);
//       response.setPageNumber(page.getNumber());
//       response.setPageSize(page.getSize());
//       response.setTotalElements(page.getTotalElements());
//       response.setTotalPages(page.getTotalPages());
//       response.setLastPage(page.isLast());

        PageableResponse<UserDto> response=Helper.getPageableResponse(page,UserDto.class);
       return response;
    }

    @Override
    public UserDto getUserById(String userId) {
        User user = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException());

        return entityToDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepo.findByEmail(email);
        if(user==null)
        {
            new ResourceNotFoundException("User not found");
        }
        return entityToDto(user);
    }

    @Override
    public List<UserDto> searchUse(String keyword)
    {
        List<User> users = userRepo.findByNameContaining(keyword);

        List<UserDto> userdtos = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        return userdtos;
    }

    @Override
    public UserDto findByEmailAndPassword(String email, String password) {
        User user = userRepo.findByEmailAndPassword(email,password);


        return entityToDto(user);
    }

    @Override
    public void deleteUser(String id)
    {
        userRepo.deleteById(id);
    }


    private User dtoToEntity(UserDto userdto)
    {
//        User user = User.builder()
//                .userId(userdto.getUserId())
//                .name(userdto.getName())
//                .email(userdto.getEmail())
//                .password(userdto.getPassword())
//                .about(userdto.getAbout())
//                .gender(userdto.getGender())
//                .imageName(userdto.getImageName())
//                .build();
//        return user;
        return mapper.map(userdto,User.class);
    }

    private UserDto entityToDto(User user)
    {
//        UserDto userdto = UserDto.builder()
//                .userId(user.getUserId())
//                .name(user.getName())
//                .email(user.getEmail())
//                .password(user.getPassword())
//                .about(user.getAbout())
//                .gender((user.getGender()))
//                .imageName((user.getImageName()))
//                .build();
//        return userdto;

        return mapper.map(user,UserDto.class);
    }



}
