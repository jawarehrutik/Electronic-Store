package com.lcwd.electronic.store.dtos;

import com.lcwd.electronic.store.validate.ImageNameValid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {


    private String userId;
    @Size(max = 20,message = "Max size of name must be 20")
    private String name;

    //@Pattern(regexp = "",message = "Invalid user email")
    @NotBlank(message = "Write email")
    @Email(message = "Invalid Email")
    private String email;

    @NotBlank(message="Invalid message")
    private String password;

    @Size(min = 4,max = 6,message = "Invalid gender")
    private String gender;

   // @NotBlank(message = "Write something about")

    @NotBlank(message = "enter about")
    private String about;

    @ImageNameValid
    private String imageName;



}
