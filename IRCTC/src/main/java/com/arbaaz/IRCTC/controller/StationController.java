package com.arbaaz.IRCTC.controller;

import com.arbaaz.IRCTC.model.Station;
import com.arbaaz.IRCTC.service.StationService;
import com.arbaaz.IRCTC.wrapper.StationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/irctc/stations")
public class StationController {

    @Autowired
    StationService stationService;

    @PostMapping
    public ResponseEntity<?> addStation( @RequestBody StationRequest stationRequest){
        return stationService.addStation(stationRequest);
    }


}
