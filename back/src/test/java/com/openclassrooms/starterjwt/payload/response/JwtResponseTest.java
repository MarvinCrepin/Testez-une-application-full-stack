package com.openclassrooms.starterjwt.payload.response;

import com.openclassrooms.starterjwt.payload.request.SignupRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class JwtResponseTest {
    private Validator validator;
    String accessToken = "jwt";
    Long id = 1l;
    String username = "john@doe.com";
    String firstName = "John";
    String lastName = "Doe";

    Boolean admin = true;
    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidSignUpRequest() {

        Boolean admin = true;

        JwtResponse jwtResponse = new JwtResponse(this.accessToken,2l,this.username,this.firstName,this.lastName,false);

        jwtResponse.setAdmin(this.admin);

        Set<ConstraintViolation<JwtResponse>> violations = validator.validate(jwtResponse);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidUserDto() {

        JwtResponse jwtResponse = new JwtResponse(this.accessToken,2l,this.username,this.firstName,this.lastName,false);
        Set<ConstraintViolation<JwtResponse>> violations = validator.validate(jwtResponse);
        assertEquals(0, violations.size());
    }



}