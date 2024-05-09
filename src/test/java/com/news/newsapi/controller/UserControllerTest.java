package com.news.newsapi.controller;

import com.news.newsapi.entity.AuthRequest;
import com.news.newsapi.entity.UserInfo;
import com.news.newsapi.logout.BlackList;
import com.news.newsapi.service.JWTService;
import com.news.newsapi.service.UserInfoService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private UserInfoService userInfoService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JWTService jwtService;

    @Mock
    private BlackList blackList;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testWelcome() {
        assertEquals("Welcome to Spring Security", userController.welcome());
    }

    @Test
    void testAddUser() {
        UserInfo userInfo = new UserInfo();
        when(userInfoService.addUser(userInfo)).thenReturn("User added successfully");
        assertEquals("User added successfully", userController.addUser(userInfo));
    }

    @Test
    void testLogin() {
        AuthRequest authRequest = new AuthRequest("username", "password");
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(jwtService.generateToken("username")).thenReturn("sample_token");

        assertEquals("sample_token", userController.login(authRequest));
    }

    @Test
    void testLogoutUser() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Authorization")).thenReturn("Bearer sample_token");
        userController.logoutUser(request);

        verify(blackList).blacKListToken("sample_token");
    }

    @Test
    void testGetAllUsers() {
        List<UserInfo> userList = List.of(new UserInfo(), new UserInfo());
        when(userInfoService.getAllUser()).thenReturn(userList);

        assertEquals(userList, userController.getAllUsers());
    }

    @Test
    void testGetUsers() {
        UserInfo user = new UserInfo();
        when(userInfoService.getUser(1)).thenReturn(user);

        assertEquals(user, userController.getUsers(1));
    }
}
