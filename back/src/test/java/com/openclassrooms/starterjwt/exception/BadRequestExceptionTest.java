package com.openclassrooms.starterjwt.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.junit.jupiter.api.Assertions.*;

class BadRequestExceptionTest {
    @Test
    public void testBadRequestException() {
        BadRequestException badRequestException = new BadRequestException();
        assertEquals(HttpStatus.BAD_REQUEST, badRequestException.getClass().getAnnotation(ResponseStatus.class).value());
    }

}