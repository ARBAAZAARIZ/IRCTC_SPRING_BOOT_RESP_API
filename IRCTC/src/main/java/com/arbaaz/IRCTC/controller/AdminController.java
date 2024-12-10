package com.arbaaz.IRCTC.controller;

import com.arbaaz.IRCTC.model.Admin;
import com.arbaaz.IRCTC.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/irctc/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

//     controller to create new admin
    @PostMapping
    public ResponseEntity<?> createNewAdmin( @Valid @RequestBody Admin admin){
        return adminService.createNewAdmin(admin);
    }


    @GetMapping
    public ResponseEntity<?> getAllAdmin(){
        return adminService.getAllAdmin();
    }

    @GetMapping("/by-username/{userName}")
    public ResponseEntity<?> getAdminByUserName( @PathVariable String userName){
       return adminService.getAdminByUserName(userName);
    }

    @GetMapping("/by-id/{id}")
    public ResponseEntity<?> getAdminById( @PathVariable int id){
        return adminService.getAdminById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAdminById( @PathVariable int id, @Valid @RequestBody Admin admin){
        return adminService.updateAdminById(id,admin);
    }

    @DeleteMapping("/{id}")
    public String deleteAdminById( @PathVariable int id){
        return adminService.deleteAdminById(id);
    }

    @GetMapping("/adminUserName-{username}/password-{password}/role-{role}")
    public ResponseEntity<?> getAdminByUSerNamePasswordRole( @PathVariable String username,
                                                             @PathVariable String password,
                                                             @PathVariable String role){
        return adminService.getAdminByUSerNamePasswordRole(username,password,role);
    }

}
