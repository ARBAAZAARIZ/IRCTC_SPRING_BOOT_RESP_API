package com.arbaaz.IRCTC.repository;

import com.arbaaz.IRCTC.model.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrainRepository extends JpaRepository<Train,Integer> {

    @Query("SELECT t.id FROM Train t WHERE t.trainNumber = :trainNumber")
    Optional<Integer> findIdByTrainNumber(@Param("trainNumber") int trainNumber);



}
