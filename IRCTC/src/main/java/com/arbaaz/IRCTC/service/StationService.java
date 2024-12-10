package com.arbaaz.IRCTC.service;

import com.arbaaz.IRCTC.model.Admin;
import com.arbaaz.IRCTC.model.Station;
import com.arbaaz.IRCTC.repository.AdminRepository;
import com.arbaaz.IRCTC.repository.StationRepository;
import com.arbaaz.IRCTC.wrapper.ApiWrapper;
import com.arbaaz.IRCTC.wrapper.StationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class StationService {

    @Autowired
    StationRepository stationRepository;

    @Autowired
    ApiWrapper apiWrapper;

    @Autowired
    AdminRepository adminRepository;

    public ResponseEntity<?> addStation(StationRequest stationRequest){

        Admin admin=adminRepository.findByAdminUserNameAndPasswordAndRole(stationRequest.getAdminUserName(),
                stationRequest.getPassword(),stationRequest.getRole()).orElseThrow(
                ()->{
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,"Unauthorized: Admin credentials are incorrect.");
                }
        );

        List<Station> stationList = new ArrayList<>();

        for(StationRequest.stationDetail newStation:stationRequest.getStationDetails()){
            Station station=new Station();
            station.setStationName(newStation.getStationName());
            station.setCity(newStation.getCity());
            stationList.add(stationRepository.save(station));
        }

        apiWrapper.setMessage("Stations add successfully");
        apiWrapper.setData(stationList);

        return new ResponseEntity<>(apiWrapper, HttpStatus.CREATED);
    }

}
