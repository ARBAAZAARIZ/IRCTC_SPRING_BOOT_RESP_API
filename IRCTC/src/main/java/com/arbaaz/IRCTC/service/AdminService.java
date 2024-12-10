package com.arbaaz.IRCTC.service;

import com.arbaaz.IRCTC.exception.NoDetailsFoundException;
import com.arbaaz.IRCTC.model.Admin;
import com.arbaaz.IRCTC.repository.AdminRepository;
import com.arbaaz.IRCTC.wrapper.ApiWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    ApiWrapper apiWrapper;

//    code to create new admin
    public ResponseEntity<?> createNewAdmin(Admin admin){
        Admin createdAdmin=adminRepository.save(admin);

        apiWrapper.setMessage("New Admin created successfully");
        apiWrapper.setData(createdAdmin);

        return  new ResponseEntity<>(apiWrapper, HttpStatus.CREATED);
    }


//    code to get all admins or to get admin list
    public ResponseEntity<?> getAllAdmin(){
        List<Admin> admins=adminRepository.findAll();
        if(admins.isEmpty()){
            throw new NoDetailsFoundException("No records in database, please create some..............");
        }

        apiWrapper.setMessage("New Admin created successfully");
        apiWrapper.setData(admins);

        return  new ResponseEntity<>(apiWrapper, HttpStatus.CREATED);

    }

    public ResponseEntity<?> getAdminByUserName(String userName){
        Admin admin=adminRepository.findByAdminUserName(userName).orElseThrow(
                ()->{
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,"given your name is not a admin : " + userName);
                }
        );
        apiWrapper.setMessage("Admin Details");
        apiWrapper.setData(admin);

        return  new ResponseEntity<>(apiWrapper, HttpStatus.CREATED);

    }

    public ResponseEntity<?> getAdminById(int id){
        Admin admin=adminRepository.findById(id).orElseThrow(
                ()->{
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,"given id is not present in database : " + id);
                }
        );
        apiWrapper.setMessage("Admin Details with id : " + id);
        apiWrapper.setData(admin);

        return  new ResponseEntity<>(apiWrapper, HttpStatus.CREATED);
    }

    public ResponseEntity<?> updateAdminById(int id,Admin admin){
        Admin foundAdmin=adminRepository.findById(id).orElseThrow(
                ()->{
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,"given id is not present in database : " + id);
                }
        );
        admin.setId(id);
       admin.setCreatedAt( foundAdmin.getCreatedAt());
       adminRepository.save(admin);

        apiWrapper.setMessage("Admin updated successfully");
        apiWrapper.setData(adminRepository.findById(id));

        return  new ResponseEntity<>(apiWrapper, HttpStatus.CREATED);

    }

    public String deleteAdminById(int id){
        Admin foundAdmin=adminRepository.findById(id).orElseThrow(
                ()->{
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,"given id is not present in database : " + id);
                }
        );

        adminRepository.deleteById(id);

        return  "your account successfully deleted";
    }


    public ResponseEntity<?> getAdminByUSerNamePasswordRole(String username, String password,String role){
        Admin admin=adminRepository.findByAdminUserNameAndPasswordAndRole(username,password,role).orElseThrow(
                ()->{
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,"Unauthorized: Admin credentials are incorrect.");
                }
        );
        apiWrapper.setMessage("Admin found ");
        apiWrapper.setData(admin);

        return new ResponseEntity<>(apiWrapper,HttpStatus.FOUND);

    }

}
