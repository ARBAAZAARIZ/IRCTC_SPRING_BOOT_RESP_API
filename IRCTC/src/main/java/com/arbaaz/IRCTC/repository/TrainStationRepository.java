package com.arbaaz.IRCTC.repository;

import com.arbaaz.IRCTC.model.TrainStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrainStationRepository extends JpaRepository<TrainStation,Integer> {

    List<TrainStation> findByTrainId(int train_id);

    @Query(value = "SELECT train_id FROM train_station WHERE station_id = :scorceId INTERSECT SELECT train_id FROM train_station WHERE station_id = :destinatonId", nativeQuery = true)
    List<Integer> findTrainIDByOriginDestinationStation(@Param("scorceId") int scorceId, @Param("destinatonId") int destinatonId);


}
