package com.lcwd.electronic.store.services;

import com.lcwd.electronic.store.dtos.UserDto;

import java.util.List;

public interface UserService {

    public UserDto createUser(UserDto userdto);

    public UserDto updateUser(UserDto userdto,String userId);

    public List<UserDto> getAllUser();

    public UserDto getUserById(String userId);

    public UserDto getUserByEmail(String email);

    public List<UserDto> searchUse(String keyword);

    public UserDto findByEmailAndPassword(String email,String password);

    public void deleteUser(String id);



}
