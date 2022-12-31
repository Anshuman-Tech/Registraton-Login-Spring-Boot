package com.anshuman.springbootspringsecurityregistrationlogin.Service;

import com.anshuman.springbootspringsecurityregistrationlogin.DTO.UserRegistrationDTO;
import com.anshuman.springbootspringsecurityregistrationlogin.Entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


public interface UserService extends UserDetailsService {

    User registerUser(UserRegistrationDTO userRegistrationDTO);

}
