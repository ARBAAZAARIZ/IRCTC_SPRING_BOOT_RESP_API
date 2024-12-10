package com.arbaaz.IRCTC.service;

import com.arbaaz.IRCTC.exception.NoDetailsFoundException;
import com.arbaaz.IRCTC.model.Admin;
import com.arbaaz.IRCTC.model.Station;
import com.arbaaz.IRCTC.model.Train;
import com.arbaaz.IRCTC.model.TrainStation;
import com.arbaaz.IRCTC.repository.AdminRepository;
import com.arbaaz.IRCTC.repository.StationRepository;
import com.arbaaz.IRCTC.repository.TrainRepository;
import com.arbaaz.IRCTC.repository.TrainStationRepository;
import com.arbaaz.IRCTC.wrapper.ApiWrapper;
import com.arbaaz.IRCTC.wrapper.TrainDetailWrapper;
import com.arbaaz.IRCTC.wrapper.TrainRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrainService {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    TrainRepository trainRepository;

    @Autowired
    StationRepository stationRepository;

    @Autowired
    TrainStationRepository trainStationRepository;

    @Autowired
    ApiWrapper apiWrapper;

    @Autowired
    TrainDetailWrapper trainDetailWrapper;

    public ResponseEntity<?> addNewTrain(TrainRequest trainRequest){


        Admin admin=adminRepository.findByAdminUserNameAndPasswordAndRole(trainRequest.getAdminUserName(),
                trainRequest.getPassword(),trainRequest.getRole()).orElseThrow(
                ()->{
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,"Unauthorized: Admin credentials are incorrect.");
                }
        );
       // Create and save the new train
        Train train=new Train();

        train.setTrainName(trainRequest.getTrainDetails().getTrainName());
        train.setTrainNumber(trainRequest.getTrainDetails().getTrainNumber());

        Train newTrain=trainRepository.save(train);


     // Add stations to the train
        for(TrainRequest.TrainDetails.StationSchedule stationSchedule : trainRequest.getTrainDetails().getStationSchedules()){
            TrainStation trainStation=new TrainStation();
            trainStation.setTrain(newTrain);
            Station foundstation=stationRepository.findById(stationSchedule.getStationid()).orElseThrow(
                    ()->{
                        return new ResponseStatusException(HttpStatus.NOT_FOUND,"No station found with given id : " + stationSchedule.getStationid() );
                    }
            );

            trainStation.setStation(foundstation);
            trainStation.setArrivalTime(stationSchedule.getArrivalTime());
            trainStation.setDepartureTime(stationSchedule.getDepartureTime());

            trainStationRepository.save(trainStation);

        }
//        from newTrain variable we get train name and train number
        trainDetailWrapper.setTrainName(newTrain.getTrainName());
        trainDetailWrapper.setTrainNumber(newTrain.getTrainNumber());

//        we get staion and there arrival and departure
        List <TrainStation> trainStations=trainStationRepository.findByTrainId(newTrain.getId());

        for(TrainStation trainStation: trainStations){

            Station station=stationRepository.findById(trainStation.getStation().getId()).orElseThrow();


            TrainDetailWrapper.StationDetailWrapper stationDetails= new TrainDetailWrapper.StationDetailWrapper();
           stationDetails.setStationName(station.getStationName());
           stationDetails.setArrivalTime(trainStation.getArrivalTime());
           stationDetails.setDepartureTime(trainStation.getDepartureTime());

           trainDetailWrapper.getStationDetailWrapperList().add(stationDetails);
        }

        apiWrapper.setMessage("Successfully added Train in Database");
        apiWrapper.setData(trainDetailWrapper);

        return new ResponseEntity<>(apiWrapper,HttpStatus.CREATED);

    }





    public ResponseEntity<?> getAllTrain(){
        List<Train> trainList =trainRepository.findAll();
        if(trainList.isEmpty()){
            throw new NoDetailsFoundException("no records found in database ");
        }

        apiWrapper.setData(trainList);
        apiWrapper.setMessage("All train details ");

        return new ResponseEntity<>(apiWrapper,HttpStatus.FOUND);
    }





    public ResponseEntity<?> getTrainById(int id){
        Train foundTrain=trainRepository.findById(id).orElseThrow(

                ()->{
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,"No train found with given id " + id   );
                }
        );
        apiWrapper.setData(foundTrain);
        apiWrapper.setMessage("Train detail with id : " +id );

        return new ResponseEntity<>(apiWrapper,HttpStatus.FOUND);

    }




    public ResponseEntity<?> findTrainsByOriginAndDestinationStation(String  originStation , String destinationStation){

        int originId=stationRepository.findIdByStationName(originStation).orElseThrow(

                ()->{
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,"In valid Station" + originStation);
                }

        );
        System.out.println(originId);
        int destinationId=stationRepository.findIdByStationName(destinationStation).orElseThrow(
                ()->{
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,"in valid station" + destinationStation);
                }
        );



        List<Integer> trainIds=trainStationRepository.findTrainIDByOriginDestinationStation(originId,destinationId);

        if(trainIds==null){
            return new ResponseEntity<>("no trains run's between "+ originStation + " and " + destinationStation
                    + " station ",HttpStatus.NOT_FOUND);
        }
            List<Train> trainList=new ArrayList<>();

        for (int trainId: trainIds){

          trainList.add(trainRepository.findById(trainId).orElseThrow());
        }

        apiWrapper.setData(trainList);
        apiWrapper.setMessage("This are the trains which run's between "+ originStation +
                " and " + destinationStation + " station ");

        return new ResponseEntity<>(apiWrapper,HttpStatus.FOUND );

    }


    public ResponseEntity<?> getTrainRoute(int trainNo){

        int trainId=trainRepository.findIdByTrainNumber(trainNo).orElseThrow(
                ()->{
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,"No train found with given Train Number "+ trainNo);
                }
        );

        Train train=trainRepository.findById(trainId).orElseThrow();

        trainDetailWrapper.setTrainName(train.getTrainName());
        trainDetailWrapper.setTrainNumber(train.getTrainNumber());

        List<TrainStation> trainStations= trainStationRepository.findByTrainId(trainId);

        TrainDetailWrapper.StationDetailWrapper stationDetailWrapper = new TrainDetailWrapper.StationDetailWrapper();

        for(TrainStation trainStation : trainStations){
            Station station=stationRepository.findById(trainStation.getStation().getId()).orElseThrow();
            assert false;
            stationDetailWrapper.setStationName(station.getStationName());
            stationDetailWrapper.setArrivalTime(trainStation.getArrivalTime());
            stationDetailWrapper.setDepartureTime(trainStation.getDepartureTime());

            trainDetailWrapper.getStationDetailWrapperList().add(stationDetailWrapper);
        }
            apiWrapper.setData(trainDetailWrapper);
            apiWrapper.setMessage("Here your train route details ");
        return new ResponseEntity<>(apiWrapper,HttpStatus.FOUND);

    }



}
