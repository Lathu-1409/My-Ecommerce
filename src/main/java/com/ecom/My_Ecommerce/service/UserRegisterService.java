package com.ecom.My_Ecommerce.service;

import java.util.Base64;

import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecom.My_Ecommerce.model.User;
import com.ecom.My_Ecommerce.model.UserLogin;
import com.ecom.My_Ecommerce.mongo.MongoConnection;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
@Component
public class UserRegisterService {

    @Autowired(required = false)
    MongoConnection mongo;

    public String insertUserDeatils(User userDetails) {
        try {
            // Encrypt password before inserting to DB
            String encryptedPassword = Base64.getEncoder().encodeToString(userDetails.getPassword().getBytes());
            userDetails.setPassword(encryptedPassword);

            MongoCollection<User> mongoConnect = mongo.getUserCollection();
            mongoConnect.insertOne(userDetails);
            return "Registration successful!! Thanks for your interest " + userDetails.getFirstName() + " " + userDetails.getLastName();
        } catch (Exception e) {
            if (e.getMessage().contains("dup key")) {
                return "UserName already exists - " + userDetails.getUserName();
            }
        }
        return "Failed, try again later";
    }

    public String loginCheck(UserLogin userDetails) {
        try {
            MongoCollection<User> mongoConnect = mongo.getUserCollection();
            Bson filterBson = Filters.eq("userName", userDetails.getUserName());
            User user = mongoConnect.find(filterBson).first();

            if (null == user || null == user.getUserName()) {
                return "User doesn't exist, try with a valid userName....";
            }

            // Decrypt password after reading from DB
            String decryptedPassword = new String(Base64.getDecoder().decode(user.getPassword()));

            if (decryptedPassword.equals(userDetails.getPassword())) {
                return "Login Success!! Welcome " + user.getFirstName();
            } else {
                return "Login Failed!! Try with valid password";
            }
        } catch (Exception e) {
            return "Login Failed!! Try again later...";
        }
    }

}
