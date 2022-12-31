package com.anshuman.springbootspringsecurityregistrationlogin.Service;

import com.anshuman.springbootspringsecurityregistrationlogin.DTO.UserRegistrationDTO;
import com.anshuman.springbootspringsecurityregistrationlogin.Entity.Role;
import com.anshuman.springbootspringsecurityregistrationlogin.Entity.User;
import com.anshuman.springbootspringsecurityregistrationlogin.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService{

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User registerUser(UserRegistrationDTO userRegistrationDTO){
//        User user = new User(userRegistrationDTO.getFirstName(),
//                userRegistrationDTO.getLastName(),userRegistrationDTO.getEmail(),
//                userRegistrationDTO.getPassword(),Arrays.asList(new Role("ROLE_USER")));

        User user = new User();
        user.setFirstName(userRegistrationDTO.getFirstName());
        user.setLastName(userRegistrationDTO.getLastName());
        user.setEmail(userRegistrationDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
        user.setRoles(Arrays.asList(new Role("ROLE_USER")));

        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username);
        if(user==null){
            throw new UsernameNotFoundException("Invalid username or password");
        }

        // This user is of spring security. It is the implementation of the UserDetails interface
        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),mapRolesToAuthorities(user.getRoles()));
    }

    //Converts roles into SimpleGrantedAuthority.
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}