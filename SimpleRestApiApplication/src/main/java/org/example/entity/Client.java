package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotBlank(message = "Name must not be blank")
    private String name;

    @Column(name = "phoneNo")
    @NotBlank(message = "Phone number must not be blank")
    private String phoneNo;

    @Column(name = "email")
    @NotBlank(message = "Email must not be blank")
    @Email(message = "Invalid email format")
    private String email;

}
