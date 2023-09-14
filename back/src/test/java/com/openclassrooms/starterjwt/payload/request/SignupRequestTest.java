package com.openclassrooms.starterjwt.payload.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SignupRequestTest {

    private SignupRequest signupRequest;
    private Validator validator;

    @BeforeEach
    public void setUp() {
        signupRequest = new SignupRequest();
        signupRequest.setEmail("test@example.com");
        signupRequest.setFirstName("John");
        signupRequest.setLastName("Doe");
        signupRequest.setPassword("password");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidSignupRequest() {
        assertTrue(validator.validate(signupRequest).isEmpty());
    }

    @Test
    public void testBlankEmail() {
        signupRequest.setEmail("");
        assertFalse(validator.validate(signupRequest).isEmpty());
    }

    @Test
    public void testInvalidEmailFormat() {
        signupRequest.setEmail("invalid-email");
        assertFalse(validator.validate(signupRequest).isEmpty());
    }

    @Test
    public void testShortFirstName() {
        signupRequest.setFirstName("J");
        assertFalse(validator.validate(signupRequest).isEmpty());
    }

    @Test
    public void testLongFirstName() {
        signupRequest.setFirstName("VeryLongFirstNameThatExceedsMaxLength");
        assertFalse(validator.validate(signupRequest).isEmpty());
    }

    @Test
    public void testShortLastName() {
        signupRequest.setLastName("D");
        assertFalse(validator.validate(signupRequest).isEmpty());
    }

    @Test
    public void testLongLastName() {
        signupRequest.setLastName("VeryLongLastNameThatExceedsMaxLength");
        assertFalse(validator.validate(signupRequest).isEmpty());
    }

    @Test
    public void testShortPassword() {
        signupRequest.setPassword("pass");
        assertFalse(validator.validate(signupRequest).isEmpty());
    }

    @Test
    public void testLongPassword() {
        signupRequest.setPassword("VeryLongPasswordThatExceedsMaxLength1234567890");
        assertFalse(validator.validate(signupRequest).isEmpty());
    }
}
