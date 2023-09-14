package com.openclassrooms.starterjwt.payload.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginRequestTest {
    @Test
    void loginRequestTest(){
        String email = "test@test.com";
        String password = "password";

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);
        assertEquals(email,loginRequest.getEmail());
        assertEquals(password, loginRequest.getPassword());
    }

}