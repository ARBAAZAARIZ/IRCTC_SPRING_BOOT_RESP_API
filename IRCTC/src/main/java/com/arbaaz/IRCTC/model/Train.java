package com.arbaaz.IRCTC.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Train {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    @Size(min = 10 , message = "The train name should have more than 10 characters; therefore, include 'Express' in the name.")
    @NotBlank(message = "Train name is mandatory")
    private String trainName;

    @Column(nullable = false,unique = true)
   @Digits(integer = 6, fraction = 0, message = "Train number must contain 5-6 digits")
    private int trainNumber;



//    @ManyToMany
//    @JoinTable(
//            name = "train_station",
//            joinColumns = @JoinColumn(name = "train_id"),
//            inverseJoinColumns = @JoinColumn(name = "station_id")
//    )
//    @JsonManagedReference
//    private List<Station> stations;



    @OneToMany(mappedBy = "train", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<TrainStation> trainStations;

}
