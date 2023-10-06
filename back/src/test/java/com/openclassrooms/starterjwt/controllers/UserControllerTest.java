package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.mapper.UserMapper;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import com.openclassrooms.starterjwt.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class UserControllerTest {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private UserController userController;
    @Test
    public void findById() {
        Long id = 1L;
        String email = "john@doe.com";
        String password = "johndoe123";
        String firstname = "John";
        String lastname = "Doe";
        boolean admin = false;

        User user = User.builder()
                .id(id)
                .email(email)
                .firstName(firstname)
                .lastName(lastname)
                .password(password)
                .admin(admin).build();

        UserDto dto = new UserDto();
        dto.setId(id);
        dto.setEmail(email);
        dto.setFirstName(firstname);
        dto.setLastName(lastname);
        dto.setPassword(password);
        dto.setAdmin(admin);


        ResponseEntity<?> response = userController.findById(id.toString());
        UserDto responseBody = (UserDto) response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, responseBody);
    }

    @Test
    public void findByIdNotFound() {
        Long id = 1L;

        ResponseEntity<?> response = userController.findById(id.toString());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void findByIdWithEmptyId() {
        UserController userController = new UserController(userService, userMapper);
        ResponseEntity<?> response = userController.findById("");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void delete() {
        Long id = 1L;
        String email = "john@doe.com";
        String password = "johndoe123";
        String firstname = "John";
        String lastname = "Doe";
        boolean admin = false;

        User user = User.builder()
                .id(id)
                .email(email)
                .firstName(firstname)
                .lastName(lastname)
                .password(password)
                .admin(admin).build();

        UserDetailsImpl userDetails = UserDetailsImpl.builder()
                .id(id)
                .username(email)
                .firstName(firstname)
                .lastName(lastname)
                .password(password)
                .admin(admin).build();

        ResponseEntity<?> response = userController.save(id.toString());

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void deleteNotFound() {
        Long id = 1L;

        ResponseEntity<?> response = userController.save(id.toString());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void deleteBadRequest() {
        UserController userController = new UserController(userService, userMapper);
        ResponseEntity<?> response = userController.save("");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
