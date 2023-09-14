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

    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtUtils jwtUtils;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRepository userRepository;
    private final String email = "john@doe.com";
    private final String password = "johndoe1";
    private final String firstname = "John";
    private final String lastname = "Doe";
    @Test
    public void AuthenticateUserOk() {
        Long id = 1L;

        boolean isAdmin = false;

        UserDetailsImpl userDetails = UserDetailsImpl
                .builder()
                .username(this.email)
                .firstName(this.firstname)
                .lastName(this.lastname)
                .id(id)
                .password(this.password)
                .build();

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null);

        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(this.email, this.password))).thenReturn(authentication);
        when(jwtUtils.generateJwtToken(authentication)).thenReturn("jwt");
        when(userRepository.findByEmail(this.email)).thenReturn(
                Optional.of(User
                        .builder()
                        .id(id)
                        .email(this.email)
                        .password(this.password)
                        .firstName(this.firstname)
                        .lastName(this.lastname)
                        .admin(isAdmin)
                        .build()));

        AuthController authController = new AuthController(authenticationManager, passwordEncoder, jwtUtils, userRepository);
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
        when(userRepository.existsByEmail(email)).thenReturn(false);
        when(passwordEncoder.encode(password)).thenReturn("hashed");
        when(userRepository.save(any(User.class))).thenReturn(new User());

        AuthController authController = new AuthController(authenticationManager, passwordEncoder, jwtUtils, userRepository);
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
        when(userRepository.existsByEmail(this.email)).thenReturn(true);
        AuthController authController = new AuthController(authenticationManager, passwordEncoder, jwtUtils, userRepository);
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail((this.email));
        signupRequest.setPassword((this.email));
        ResponseEntity<?> response = authController.registerUser(signupRequest);

        MessageResponse messageResponse = (MessageResponse) response.getBody();
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error: Email is already taken!", messageResponse.getMessage());
    }
}