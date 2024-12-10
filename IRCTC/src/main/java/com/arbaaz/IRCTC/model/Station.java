package com.arbaaz.IRCTC.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;


@Entity
@Data
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false,unique = true)
    @Size(min = 2,message = "Station name must be greater then 2 character or equal to 2 character")
    private String stationName;

    @Size(min = 3,max = 30,message = "City name must be between 3 to 30 character")
    private String city;

    @OneToMany(mappedBy = "station" , cascade = CascadeType.ALL , orphanRemoval = true)
    @JsonIgnore
    private List<TrainStation> trainStations;



}
