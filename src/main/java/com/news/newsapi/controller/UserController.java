package com.news.newsapi.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.news.newsapi.entity.UserResponse;
import com.news.newsapi.logout.BlackList;
import com.news.newsapi.service.JWTService;
import jakarta.persistence.Entity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.news.newsapi.entity.AuthRequest;
import com.news.newsapi.entity.UserInfo;
import com.news.newsapi.service.UserInfoService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v2/auth")
@CrossOrigin
public class UserController {
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JWTService jwtService;

    @Autowired
    BlackList blackList;

    //Create welcome method with @GetMappring
    @RequestMapping("/welcome")
    public String welcome(){
        return "Welcome to Spring Security";
    }

    @RequestMapping("/addUser")
    public String addUser(@RequestBody UserInfo userInfo){
        return userInfoService.addUser(userInfo);
    }
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> login(@RequestBody AuthRequest authRequest){

       Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
       UserResponse response = new UserResponse();

       if(authentication.isAuthenticated()){
           System.out.println("Login Successfully");
            response.setMessage("Login Successfully");
            response.setToken(jwtService.generateToken(authRequest.getUsername()));
            return new ResponseEntity<>(response, HttpStatusCode.valueOf(200));
        }else{
           System.out.println("Invalid User request");
           response.setMessage("Invalid User request");
           response.setToken(null);
           return new ResponseEntity<>(response, HttpStatusCode.valueOf(403));
        }
    }
    @PostMapping("/logout")
    @PreAuthorize("hasAuthority('USER_ROLES') or hasAuthority('ADMIN_ROLES')")
    public String logoutUser(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        String token= null;
        if(authHeader !=null && authHeader.startsWith("Bearer")){
            token = authHeader.substring(7);
        }
        blackList.blacKListToken(token);
        return "You have successfully logged out !!";
    }
    //create method to getAllUsers() @GetMapping return type List of UserInfo
    @GetMapping("/getAllUsers")
    @PreAuthorize("hasAuthority('ADMIN_ROLES') or hasAuthority('USER_ROLES')")
    public List<UserInfo> getAllUsers(){
        return userInfoService.getAllUser();
    }
    //create method to getUserById() @GetMapping return type UserInfo
    @GetMapping("/getUsers/{id}")
    @PreAuthorize("hasAuthority('USER_ROLES')")
    public UserInfo getUsers(@PathVariable Integer id){
        return userInfoService.getUser(id);
    }

}