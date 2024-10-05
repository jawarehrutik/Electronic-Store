package com.lcwd.electronic.store.entities;

//import jakarta.persistence.Column;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@ToString
@Table(name="users")
public class User {

    @Id
    @Column(name="user_id")
    private String userId;

    @Column(name="user_name")
    private String name;

    @Column(name="user_email",unique = true)
    private String email;

    @Column(name="user_password",length = 10)
    private String password;

    @Column(name="user_gender")
    private String gender;

    @Column(length=100)
    private String about;

    @Column(name="user_image_name")
    private String imageName;


}
