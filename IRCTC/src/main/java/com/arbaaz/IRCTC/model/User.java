package com.arbaaz.IRCTC.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Username is mandatory")
    @Size(min = 3,max = 20,message = "username must be between 3 to 20 characters")
    @Column(unique = true)
    private String userName;

//    ^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$:
//
//(?=.*[A-Z]): Ensures at least one uppercase letter.
//
//(?=.*[a-z]): Ensures at least one lowercase letter.
//
//(?=.*\\d): Ensures at least one digit.
//
//.{8,}$: Ensures the password is at least 8 characters long.

    @NotBlank(message = "Username is mandatory")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$",
    message = "password must contain least 1 lowercase, 1 uppercase, 1 number " +
            "and overall combination should be 8 character ")
    private String password;

    @NotNull(message = "Phone number is mandatory")
    @Pattern(regexp = "^\\d{10}$",message =" phone number must be 10 digits")
    @Size(min = 10,max = 10,message = "phone number must be 10 digit not more then 10 and less then 10")
    private String phoneNumber;

    @Column(nullable = false,unique = true)
    @Email(message = "Email should be valid ")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotBlank(message = "City is mandatory")
    private String city;

    @NotNull(message = "Date of birth is mandatory")
    private LocalDate dateOfBirth;



    @Column(nullable = true)
    @Pattern(regexp = "^(Male|male|Female|female|Other|other)$",message = "Gender must" +
            "be Male, Female, Other" )
    private String gender;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bankAccountId")
    private BankAccount bankAccount;

}
