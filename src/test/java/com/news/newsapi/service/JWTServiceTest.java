package com.news.newsapi.service;

import com.news.newsapi.logout.BlackList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JWTServiceTest {

    @Mock
    private BlackList blackList;

    @InjectMocks
    private JWTService jwtService;

    private final String testUsername = "testUser";
    private final String testToken = "!@#$FDGSDFGSGSGSGSHSHSHSSHGFFDSGSFGSSGHHHHHHHFDDFDFDFDFFHSDFSDFSFSHFFGHFDFDDFDFDFDFDFDFDFDFDFDFDFDFFSFSDFSFSFSFGEDDSDSDSDSDSDSDSDSD";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGenerateToken() {
        String token = jwtService.generateToken(testUsername);
        assertNotNull(token);
        // You can add more assertions related to token content if needed
    }

    @Test
    void testExtractUserName() {
        String extractedUsername = jwtService.extractUserName(testToken);
        assertEquals(testUsername, extractedUsername);
    }

    @Test
    void testExtractExpiration() {
        Date expirationDate = jwtService.extractExpiration(testToken);
        assertNotNull(expirationDate);
        // You can add more assertions related to expiration date if needed
    }

    @Test
    void testIsTokenExpired() {
        // Assuming the token is not expired
        assertFalse(jwtService.isTokenExpired(testToken));
    }

    @Test
    void testValidateToken_ValidToken() {
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(testUsername);
        when(blackList.isBlackListed(testToken)).thenReturn(false);

        assertTrue(jwtService.validateToken(testToken, userDetails));
    }

    @Test
    void testValidateToken_ExpiredToken() {
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(testUsername);
        when(blackList.isBlackListed(testToken)).thenReturn(false);

        // Assuming the token is expired
        when(jwtService.isTokenExpired(testToken)).thenReturn(true);

        assertFalse(jwtService.validateToken(testToken, userDetails));
    }

    @Test
    void testValidateToken_BlacklistedToken() {
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(testUsername);
        when(blackList.isBlackListed(testToken)).thenReturn(true);

        assertFalse(jwtService.validateToken(testToken, userDetails));
    }
}
