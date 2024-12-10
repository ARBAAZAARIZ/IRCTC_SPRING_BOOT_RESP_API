package com.arbaaz.IRCTC.repository;

import com.arbaaz.IRCTC.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StationRepository extends JpaRepository<Station,Integer> {

     @Query("SELECT s.id FROM Station s WHERE s.stationName = :stationName")
     Optional<Integer> findIdByStationName(@Param("stationName") String stationName);


}
