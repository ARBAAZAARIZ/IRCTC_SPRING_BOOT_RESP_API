package com.arbaaz.IRCTC.controller;

import com.arbaaz.IRCTC.model.BankAccount;
import com.arbaaz.IRCTC.model.User;
import com.arbaaz.IRCTC.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/irctc/user")
public class UserController {

    @Autowired
    UserService userService;

    // controller to create new user
    @PostMapping
    public ResponseEntity<?> createNewUser(@RequestBody User user){
        return userService.createNewUser(user);
    }

    // controller to get user by id
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById( @PathVariable int id){
      return   userService.getUserById(id);
    }

    // to get all users
    @GetMapping
    public ResponseEntity<?> getAllUSer(){
        return userService.getAllUSer();
    }

    // to update bank details of a user
    @PutMapping("/{userName}/update-bank")
    public ResponseEntity<?> updateBankAccount(
           @PathVariable String userName,
           @RequestBody BankAccount bankAccount){
        return userService.updateBankAccount(userName,bankAccount);
    }

    // to get users by bank name
    @GetMapping("/bank/{bankName}")
    public ResponseEntity<?> getUserByBankName( @PathVariable String bankName){
      return  userService.getUserByBankName(bankName);
    }


    // to get user by city and gender
    @GetMapping("/city/{city}/gender/{gender}")
    public ResponseEntity<?> getUserByCityAndGemder( @PathVariable String city, @PathVariable String gender){
        return userService.getUserByCityAndGemder(city,gender);
    }

    // get user who created account between two dates
    @GetMapping("/dates-between/date1-{date1}/date2-{date2}")
    public ResponseEntity<?> getUserByBetweenTwoDates(@PathVariable Instant date1, @PathVariable Instant date2){
        return userService.getUserByBetweenTwoDates(date1,date2);
    }

    //  updating user by user id
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUserByIdWithOutBankDetail( @PathVariable int id, @RequestBody User user){
        return userService.updateUserByIdWithOutBankDetail(id,user);
    }

    // deleting user by id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserByID( @PathVariable int id){
       return userService.deleteUserByID(id);
    }

//    getting bank name
    @GetMapping("/{userName}/bankname")
    public ResponseEntity<?> getBankNameByUserName( @PathVariable String userName){
       return userService.getBankNameByUserName(userName);
    }

}
