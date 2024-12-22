package com.ecom.My_Ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.My_Ecommerce.model.User;
import com.ecom.My_Ecommerce.model.UserLogin;
import com.ecom.My_Ecommerce.service.UserRegisterService;
@RestController
public class UserRegisterController {
@Autowired
UserRegisterService service;

 @PostMapping("/api/register")
    public ResponseEntity<Object> registerUser(@RequestBody User request) {
       
        System.out.println("Request received: "+request.toString());

        String response= service.insertUserDeatils(request);

        System.out.println("response : "+response);

        return ResponseEntity.ok(response);
    }
    

    @PostMapping("/api/login")
    public ResponseEntity<Object> loginUser(@RequestBody UserLogin request) {
       
        System.out.println("Request received: "+request.toString());

        String response= service.loginCheck(request);

        System.out.println("response : "+response);

        return ResponseEntity.ok(response);
    }
   

}
