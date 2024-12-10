package com.arbaaz.IRCTC.service;

import com.arbaaz.IRCTC.exception.InValidAgeException;
import com.arbaaz.IRCTC.exception.NoDetailsFoundException;
import com.arbaaz.IRCTC.model.BankAccount;
import com.arbaaz.IRCTC.model.User;
import com.arbaaz.IRCTC.repository.BankRepository;
import com.arbaaz.IRCTC.repository.UserRepository;
import com.arbaaz.IRCTC.wrapper.ApiWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class UserService {

//    object of User repository
    @Autowired
    UserRepository userRepository;

//    object of Response wrapper
    @Autowired
    ApiWrapper apiWrapper;

//    object of bank repository
    @Autowired
    BankRepository bankRepository;



//    adding new user
    public ResponseEntity<?> createNewUser(User user){

        LocalDate date=user.getDateOfBirth();

        if(!isValidAge(date)){
            throw new InValidAgeException("age must be 16 or 16+ to register you have to wait " + (2024 -date.getYear()-(16)) + " year");
        }

        User createdUser=userRepository.save(user);

        apiWrapper.setMessage("Registered successfully");
        apiWrapper.setData(createdUser);

        return new ResponseEntity<>(apiWrapper, HttpStatus.CREATED);

    }

//    getting all users
    public ResponseEntity<?> getAllUSer(){

        List<User> userList=userRepository.findAll();
        if(userList.isEmpty()){
            throw new NoDetailsFoundException("No records in database, Insert some");
        }

        apiWrapper.setMessage("Here are all the user details ");
        apiWrapper.setData(userList);

        return new ResponseEntity<>(apiWrapper, HttpStatus.FOUND);
    }

    private boolean isValidAge(LocalDate date){
        return Period.between(date,LocalDate.now()).getYears() >=16;
    }


//    updating bank account
    public ResponseEntity<?> updateBankAccount(
            String userName,
            BankAccount bankAccount){
         User foundUser= userRepository.findByUserName(userName).orElseThrow(
                 ()->{
                     return new ResponseStatusException(HttpStatus.NOT_FOUND,"No user found with given username : " + userName);
                 }
         );
         foundUser.setBankAccount(bankAccount);
         userRepository.save(foundUser);

        apiWrapper.setMessage("Updated successfully ");
        apiWrapper.setData(userRepository.findByUserName(userName));

        return new ResponseEntity<>(apiWrapper, HttpStatus.OK);
    }



    public ResponseEntity<?> getUserByBankName(String bankName){
        List<Integer> bankIds=bankRepository.findIdsByBankName(bankName);
        if(bankIds.isEmpty()){
           return new ResponseEntity<>("No user found with given bank name : " + bankName,HttpStatus.NOT_FOUND);
        }
        List<User> userList=userRepository.findByBankAccountIdIn(bankIds);
        apiWrapper.setMessage("User details with given bank : " +bankName );
        apiWrapper.setData(userList);

        return new ResponseEntity<>(apiWrapper, HttpStatus.OK);
    }

    public ResponseEntity<?> getUserByCityAndGemder(String city,String gender){
        List<User> userList=userRepository.findByCityAndGender(city,gender);

        if(userList.isEmpty()){
            apiWrapper.setMessage("No users found with given city : " + city + " and " + gender );
            apiWrapper.setData(null);

            return new ResponseEntity<>(apiWrapper, HttpStatus.NOT_FOUND);
        }

        apiWrapper.setMessage("Users found with given city : " + city + " and " + gender );
        apiWrapper.setData(userList);

        return new ResponseEntity<>(apiWrapper, HttpStatus.OK);

    }

    public ResponseEntity<?> getUserByBetweenTwoDates(Instant date1, Instant date2){
        List<User> userList=userRepository.findByCreatedAtBetween(date1,date2);
        if(userList.isEmpty()){
            apiWrapper.setMessage("No users have created account between : " + date1 + " and " + date2 );
            apiWrapper.setData(null);

            return new ResponseEntity<>(apiWrapper, HttpStatus.NOT_FOUND);
        }
        apiWrapper.setMessage(userList.size()+" Users created account between " + date1 + " and " + date2 );
        apiWrapper.setData(userList);

        return new ResponseEntity<>(apiWrapper, HttpStatus.OK);
    }

    public ResponseEntity<?> getUserById(int id){
        User user=userRepository.findById(id).orElseThrow(
                ()->{
                    return new ResponseStatusException(HttpStatus.NOT_FOUND," no records found with given id :  " + id);
                }
        );

        apiWrapper.setMessage(" Record found with given id " + id);
        apiWrapper.setData(user);

        return new ResponseEntity<>(apiWrapper, HttpStatus.OK);

    }


    public ResponseEntity<?> updateUserByIdWithOutBankDetail( int id, User user){

        User foundUser=userRepository.findById(id).orElseThrow(
                ()->{
                    return new ResponseStatusException(HttpStatus.NOT_FOUND," no records found with given id :  " + id);
                }
        );
        user.setId(id);
        user.setCreatedAt(foundUser.getCreatedAt());
        BankAccount foundBankAccount=foundUser.getBankAccount();
        user.setBankAccount(foundBankAccount);

        userRepository.save(user);

        apiWrapper.setMessage(" Updated successfully ");
        apiWrapper.setData(userRepository.findById(id));

        return new ResponseEntity<>(apiWrapper, HttpStatus.OK);
 }

        public ResponseEntity<?> deleteUserByID(int id){

            User user=userRepository.findById(id).orElseThrow(
                    ()->{
                        return new ResponseStatusException(HttpStatus.NOT_FOUND," no records found with given id :  " + id);
                    }
            );

            userRepository.deleteById(id);
            apiWrapper.setMessage(" Deleted successfully ");
            apiWrapper.setData(null);

            return new ResponseEntity<>(apiWrapper, HttpStatus.OK);

        }

        public ResponseEntity<?> getBankNameByUserName(String userName){
        String bankName=bankRepository.findBankNameByUserName(userName);
        if(bankName==null){
            apiWrapper.setMessage(" user doesn't exist with given username : " + userName);
            apiWrapper.setData(null);

            return new ResponseEntity<>(apiWrapper, HttpStatus.OK);
        }
            apiWrapper.setMessage(" Your bank name : " + bankName);
            apiWrapper.setData(null);

            return new ResponseEntity<>(apiWrapper, HttpStatus.OK);
        }



}
