package com.lcwd.electronic.store.entities;

//import jakarta.persistence.Column;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import java.util.UUID;



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
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @jakarta.persistence.PrePersist
    protected void onCreate() {
        this.userId = UUID.randomUUID().toString();
    }


}
