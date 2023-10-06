package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.payload.request.SignupRequest;
import com.openclassrooms.starterjwt.payload.response.JwtResponse;
import com.openclassrooms.starterjwt.payload.response.MessageResponse;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.security.jwt.JwtUtils;
import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class AuthControllerTest {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthController authController;
    private final String email = "john@doe.com";
    private final String password = "johndoe1";
    private final String firstname = "John";
    private final String lastname = "Doe";
    @Test
    public void AuthenticateUserOk() {
        Long id = 1L;

        boolean isAdmin = false;

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(this.email);
        loginRequest.setPassword(this.password);
        ResponseEntity<?> response = authController.authenticateUser(loginRequest);
        JwtResponse responseBody = (JwtResponse) response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(this.email, responseBody.getUsername());
        assertEquals(this.firstname, responseBody.getFirstName());
        assertEquals(this.lastname, responseBody.getLastName());
        assertEquals(id, responseBody.getId());
        assertEquals(isAdmin, responseBody.getAdmin());
        assertEquals("Bearer", responseBody.getType());
        assertNotNull(responseBody.getToken());
    }

    @Test
    public void RegisterUserOk() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail(this.email);
        signupRequest.setLastName(this.lastname);
        signupRequest.setPassword(this.password);
        signupRequest.setFirstName(this.firstname);
        ResponseEntity<?> response = authController.registerUser(signupRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void RegisterUserEmailAlreadyTaken() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail((this.email));
        signupRequest.setPassword((this.email));
        ResponseEntity<?> response = authController.registerUser(signupRequest);

        MessageResponse messageResponse = (MessageResponse) response.getBody();
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error: Email is already taken!", messageResponse.getMessage());
    }
}