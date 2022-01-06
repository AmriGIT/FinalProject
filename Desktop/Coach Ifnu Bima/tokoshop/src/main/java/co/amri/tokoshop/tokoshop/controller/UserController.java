/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.amri.tokoshop.tokoshop.controller;

import co.amri.tokoshop.tokoshop.entity.User;
import co.amri.tokoshop.tokoshop.model.Login;
import co.amri.tokoshop.tokoshop.model.Registration;
import co.amri.tokoshop.tokoshop.repository.UserRepository;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author LENOVO
 */
@RestController
@RequestMapping("/api")
public class UserController {
    private BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
    @Autowired
    private UserRepository userRepository;
    
    @PostMapping("/login")
    public ResponseEntity<String>processUserLogin(@RequestBody Login login){
        User user = userRepository.getUserByName(login.getUsername());
        if(user != null && user.getPassword().equals(login.getPassword())){
            return ResponseEntity.ok("ok");
        }
        return ResponseEntity.ok("error");
    }
    
    @PostMapping("/register")
    public ResponseEntity<String> processRegistration(
        @RequestBody Registration reg){
        User user = new User();
        user.setName(reg.getUsername());
        user.setPassword(bcrypt.encode(reg.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("ok");
    }
    
    @DeleteMapping("/user/{id}")
    @Cascade(CascadeType.DELETE)
    public ResponseEntity<String> deleteUserById(
    @PathVariable(name = "id") Integer id){
    userRepository.deleteById(id);
    return ResponseEntity.ok("ok");
    }
}
