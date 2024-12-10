package com.arbaaz.IRCTC.repository;

import com.arbaaz.IRCTC.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDate;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User,Integer> {
//   jpa to get user by  userName
    public Optional<User> findByUserName(String userName);

//    jpa to get list of users  by list of bank account ids
    public List<User> findByBankAccountIdIn(List<Integer> bankIds);

//    jpa method to get user by city and gender
    public List<User> findByCityAndGender(String city,String gender);

    // jpa method to find user who created account before 26-11-2024
    public List<User> findByCreatedAtBetween(Instant date1, Instant date2);

}
