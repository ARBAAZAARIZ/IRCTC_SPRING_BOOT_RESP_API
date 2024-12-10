package com.arbaaz.IRCTC.wrapper;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Data
 public  class TrainDetailWrapper {

    private String trainName;
    private int trainNumber;

    List<StationDetailWrapper> stationDetailWrapperList=new ArrayList<>();

    @Data
   public static class StationDetailWrapper{


        private String stationName;

        private LocalTime arrivalTime;

        private LocalTime departureTime;
    }

}
