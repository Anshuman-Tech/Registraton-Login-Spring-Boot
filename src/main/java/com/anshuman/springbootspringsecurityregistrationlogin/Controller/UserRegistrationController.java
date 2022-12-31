package com.anshuman.springbootspringsecurityregistrationlogin.Controller;

import com.anshuman.springbootspringsecurityregistrationlogin.DTO.UserRegistrationDTO;
import com.anshuman.springbootspringsecurityregistrationlogin.Entity.User;
import com.anshuman.springbootspringsecurityregistrationlogin.Service.UserService;
import com.anshuman.springbootspringsecurityregistrationlogin.Service.UserServiceImp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registration")
public class UserRegistrationController {
    private UserService userService;

    public UserRegistrationController(UserServiceImp userService){
        this.userService = userService;
    }

    @Value("${welcome.message}")
    public String message;

    @GetMapping
    public String home(){
        return message;
    }
    @PostMapping("/register")
    public User registerUser(@RequestBody UserRegistrationDTO userRegistrationDTO){
        return userService.registerUser(userRegistrationDTO);
    }

}
