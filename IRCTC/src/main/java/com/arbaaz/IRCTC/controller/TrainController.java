package com.arbaaz.IRCTC.controller;

import com.arbaaz.IRCTC.service.TrainService;
import com.arbaaz.IRCTC.wrapper.TrainRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/irctc/trains")
public class TrainController {

    @Autowired
    TrainService trainService;

    @PostMapping
    public ResponseEntity<?> addNewTrain(
           @Valid @RequestBody TrainRequest trainRequest){
       return trainService.addNewTrain(trainRequest);
    }

    @GetMapping
    public ResponseEntity<?> getAllTrain(){
        return trainService.getAllTrain();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTrainById( @PathVariable int id){
        return trainService.getTrainById(id);
    }

    @GetMapping("/ori-{originStation}/dest-{destinatoonStation}")
    public ResponseEntity<?> findTrainsByOriginAndDestinationStation(  @PathVariable String  originStation ,
                                                                       @PathVariable String destinatoonStation){
        return trainService.findTrainsByOriginAndDestinationStation(originStation,destinatoonStation);
    }

    @GetMapping("/find-train-route-{trainNo}")
    public ResponseEntity<?> getTrainRoute(  @PathVariable int trainNo){
        return trainService.getTrainRoute(trainNo);
    }
}
