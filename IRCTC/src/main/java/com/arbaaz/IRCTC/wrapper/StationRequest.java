package com.arbaaz.IRCTC.wrapper;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class StationRequest {

    @NotBlank(message = "Username is mandatory")
    @Size(min = 3,max = 20,message = "username must be between 3 to 20 characters")
    private String adminUserName;

    @NotBlank(message = "Username is mandatory")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$",
            message = "password must contain least 1 lowercase, 1 uppercase, 1 number " +
                    "and overall combination should be 8 character ")
    private String password;

    @Pattern(regexp = ("^(admin|ADMIN|Admin)$"), message = "Spelling of admin must be correct")
    @NotBlank(message = "role is mandatory")
    private String role;

    private Set<stationDetail> stationDetails;

    @Data
    public static class stationDetail{
        @Column(nullable = false,unique = true)
        @Size(min = 2,message = "Station name must be greater then 2 character or equal to 2 character")
        private String stationName;

        @Size(min = 3,max = 30,message = "City name must be between 3 to 30 character")
        private String city;
    }

}
