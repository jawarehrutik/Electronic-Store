package com.lcwd.electronic.store.controllers;

import com.lcwd.electronic.store.dtos.ApiResponseMessage;
import com.lcwd.electronic.store.dtos.ImageResponse;
import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.UserDto;
import com.lcwd.electronic.store.services.FileService;
import com.lcwd.electronic.store.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

//import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userservice;

    @Autowired
    private FileService fileService;

    @Value("user.profile.image.path")
    private String imageUploadPath;



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
    public ResponseEntity<PageableResponse<UserDto>> getAllUser(
            @RequestParam(value= "pageNumber",defaultValue = "0",required = false) int pageNumber ,
            @RequestParam(value="pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value="sortBy",defaultValue = "name",required = false) String sortBy,
            @RequestParam(value="sortDir",defaultValue = "asc",required = false) String sortDir
    ) {

        PageableResponse<UserDto> response = userservice.getAllUser(pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(response,HttpStatus.OK);
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


    //update user image
    @PostMapping("/image/{userid}")
    public ResponseEntity<ImageResponse> uploadUserImage(
            @RequestParam("userImage")MultipartFile image,
            @PathVariable String userid
    )
    {

        String imageName = fileService.uploadImage(image,imageUploadPath);

        UserDto user = userservice.getUserById(userid);
        user.setImageName(imageName);
        userservice.updateUser(user,userid);


        ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).success(true).status(HttpStatus.OK).build();
        return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
    }

    //serve user image

}

