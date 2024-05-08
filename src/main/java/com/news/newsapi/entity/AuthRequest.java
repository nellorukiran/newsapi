package com.news.newsapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Create AuthRequest class with @Data annotation, @NoArgsConstructor annotation, @AllArgsConstructor annotation and fields for username and password.
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    private String username;
    private String password;


}
