package com.lcwd.electronic.store.services.impl;

import com.lcwd.electronic.store.dtos.UserDto;
import com.lcwd.electronic.store.entities.User;
import com.lcwd.electronic.store.repositories.UserRepository;
import com.lcwd.electronic.store.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;
    @Override
    public UserDto createUser(UserDto userdto) {

        User user = dtoToEntity(userdto);

        User saveUser = userRepo.save(user);

        UserDto newDto = entityToDto(saveUser);

        return newDto;
    }

    @Override
    public UserDto updateUser(UserDto userdto, String userId) {

        User user =  userRepo.findById(userId).orElseThrow(()->new RuntimeException("User not found"));

        user.setName(userdto.getName());
        user.setAbout(userdto.getAbout());
        user.setPassword(userdto.getPassword());
        user.setGender(userdto.getGender());
        user.setImageName(userdto.getImageName());

        UserDto newuserdto = entityToDto(user);

        return newuserdto;
    }

    @Override
    public List<UserDto> getAllUser() {

        List<User> users = userRepo.findAll();

       List<UserDto> userdto=  users.stream().map(user->entityToDto(user)).collect(Collectors.toList());

        return userdto;
    }

    @Override
    public UserDto getUserById(String userId) {
        User user = userRepo.findById(userId).orElseThrow(()->new RuntimeException("User not found"));

        return entityToDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepo.findByEmail(email);

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
        User user = User.builder()
                .userId(userdto.getUserId())
                .name(userdto.getName())
                .email(userdto.getEmail())
                .password(userdto.getPassword())
                .about(userdto.getAbout())
                .gender(userdto.getGender())
                .imageName(userdto.getImageName())
                .build();
        return user;
    }

    private UserDto entityToDto(User user)
    {
        UserDto userdto = UserDto.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .about(user.getAbout())
                .gender((user.getGender()))
                .imageName((user.getImageName()))
                .build();
        return userdto;
    }



}
