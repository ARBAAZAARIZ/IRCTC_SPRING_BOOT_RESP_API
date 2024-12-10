package com.arbaaz.IRCTC.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Size(min = 3 , max = 40 , message = "Bank name must be greater then 2 ")
    @NotBlank(message = "Bank name is mandatory")
    private String bankName;

    @Size(min = 5,message = "Account number must be greater then 4")
    @Column(nullable = false,unique = true)
    @NotBlank(message = "Bank account number is mandatory")
    private String accountNumber;

    @Size(max = 6,message = " IFSC code must be between 6 character")
    private String ifscCode;

    @OneToOne(mappedBy = "bankAccount")
    @JsonIgnore
    private User user;

}
