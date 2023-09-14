package com.openclassrooms.starterjwt.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.junit.jupiter.api.Assertions.*;

class NotFoundExceptionTest {
    @Test
    public void testNotFoundException() {
        NotFoundException notFoundException = new NotFoundException();
        assertEquals(HttpStatus.NOT_FOUND, notFoundException.getClass().getAnnotation(ResponseStatus.class).value());
    }

}