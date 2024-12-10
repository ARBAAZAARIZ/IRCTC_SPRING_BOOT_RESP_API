package com.arbaaz.IRCTC.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Instant;
import java.time.LocalTime;

@Entity
@Table(name = "train_station")
@Data
public class TrainStation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "train_id")
    @JsonBackReference
    private Train train;

    @ManyToOne
    @JoinColumn(name = "station_id")
    private Station station;


    @Column(nullable = false)
    private LocalTime arrivalTime;


    @Column(nullable = false)
    private LocalTime departureTime;

}
