package com.arbaaz.IRCTC.wrapper;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalTime;
import java.util.List;

@Data
public class TrainRequest {
    // admin details
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

    private TrainDetails trainDetails;

    @Data
      public static class TrainDetails{

          @Column(nullable = false)
          @Size(min = 10 , message = "The train name should have more than 10 characters; therefore, include 'Express' in the name.")
          @NotBlank(message = "Train name is mandatory")
          private String trainName;

          @Column(nullable = false,unique = true)
          @Digits(integer = 6, fraction = 0, message = "Train number must contain 5-6 digits")
          private int trainNumber;

          private List<StationSchedule> stationSchedules;

          @Data
          public static class StationSchedule {

              @Id
              private int stationid;


              @Column(nullable = false)
              private LocalTime arrivalTime;


              @Column(nullable = false)
              private LocalTime departureTime;

          }

      }


}
