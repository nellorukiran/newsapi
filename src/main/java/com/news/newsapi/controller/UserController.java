package com.news.newsapi.controller;

import com.news.newsapi.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.news.newsapi.entity.AuthRequest;
import com.news.newsapi.entity.UserInfo;
import com.news.newsapi.service.UserInfoService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JWTService jwtService;

    //Create welcome method with @GetMappring
    @RequestMapping("/welcome")
    public String welcome(){
        return "Welcome to Spring Security";
    }

    @RequestMapping("/addUser")
    public String addUser(@RequestBody UserInfo userInfo){
        return userInfoService.addUser(userInfo);
    }
    @PostMapping("/login")
    public String login(@RequestBody AuthRequest authRequest){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if(authentication.isAuthenticated()){
            return jwtService.generateToken(authRequest.getUsername());
        }else{
            throw new UsernameNotFoundException("Invalid User request");
        }
    }
    //create method to getAllUsers() @GetMapping return type List of UserInfo
    @GetMapping("/getAllUsers")
    public List<UserInfo> getAllUsers(){
        return userInfoService.getAllUser();
    }
    //create method to getUserById() @GetMapping return type UserInfo
    @GetMapping("/getUsers/{id}")
    public UserInfo getAllUsers(@PathVariable Integer id){
        return userInfoService.getUser(id);
    }

}