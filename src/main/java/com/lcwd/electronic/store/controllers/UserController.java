package com.lcwd.electronic.store.controllers;

import com.lcwd.electronic.store.dtos.ApiResponseMessage;
import com.lcwd.electronic.store.dtos.UserDto;
import com.lcwd.electronic.store.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userservice;

    //create user
    @PostMapping
    public ResponseEntity<ApiResponseMessage> createUser(@RequestBody @Valid UserDto userdto)
    {
        UserDto dto = userservice.createUser(userdto);

        ApiResponseMessage message = ApiResponseMessage.builder()
                .message("User created Successfully")
                .success(true)
                .status(HttpStatus.OK).build();
        return new ResponseEntity<>(message,HttpStatus.OK);
    }

    //upadate user
    @PostMapping("/updateUser/{id}")
    public ResponseEntity<ApiResponseMessage> upadetUser(@RequestBody UserDto userdto,@PathVariable String id)
    {
        UserDto dto = userservice.updateUser(userdto,id);

        ApiResponseMessage message = ApiResponseMessage.builder()
                .message("User updated successfully")
                .success(true)
                .status(HttpStatus.OK).build();
        return new ResponseEntity<>(message,HttpStatus.OK);
    }

    //Get All User
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUser(
            @RequestParam(value= "pageNumber",defaultValue = "0",required = false) int pageNumber ,
            @RequestParam(value="pageSize",defaultValue = "10",required = false) int pageSize
    ) {

        List<UserDto> users = userservice.getAllUser(pageNumber,pageSize);
        return new ResponseEntity<>(users,HttpStatus.OK);
    }

    // Get user by ID
    @GetMapping("userId/{userId}")
     public ResponseEntity<UserDto> getUserById( @PathVariable String userId)
     {
         UserDto dto = userservice.getUserById(userId);

         return new ResponseEntity<>(dto,HttpStatus.OK);
     }


   @GetMapping("email/{email}")
   public ResponseEntity<UserDto> getUserByEmail( @PathVariable String email)
   {
       UserDto dto = userservice.getUserByEmail(email);

       return new ResponseEntity<>(dto,HttpStatus.OK);
   }


    //search user
    @GetMapping("keyword/{keyword}")
    public ResponseEntity<List<UserDto>> searchUse(@PathVariable String keyword){
        List<UserDto> users = userservice.searchUse(keyword);

        return new ResponseEntity<>(users,HttpStatus.OK);

    }

    //find by email and password
    @GetMapping("emailandpassword/{email}/{password}")
    public ResponseEntity<UserDto> findByEmailAndPassword(@PathVariable String email,@PathVariable String password){
        UserDto user = userservice.findByEmailAndPassword(email, password);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    //Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable String id){
        userservice.deleteUser(id);

        ApiResponseMessage message = ApiResponseMessage.builder()
                .message("User deleted successfully")
                .success(true)
                .status(HttpStatus.OK).build();
        return new ResponseEntity<>(message,HttpStatus.OK);
    }

}

