package com.arbaaz.IRCTC.repository;

import com.arbaaz.IRCTC.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin,Integer> {

//    jpa method to find admin by username
    public Optional<Admin> findByAdminUserName(String userName);

    // jpa method to find admin by username ,password,role
    public Optional<Admin> findByAdminUserNameAndPasswordAndRole(String adminUserName, String password, String role);


}
