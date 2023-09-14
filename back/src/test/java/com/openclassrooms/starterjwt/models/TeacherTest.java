package com.openclassrooms.starterjwt.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TeacherTest {

    private Teacher teacher;
    private String lastName = "Jane";
    private String firstName = "Doe";

    @BeforeEach
    public void beforeEach() {
        teacher = Teacher.builder().id(8L).lastName("John").firstName("Doe").build();
    }

    @Test
    public void testTeacherID() {
        teacher.setId(2L);

        assertEquals(2, teacher.getId());
    }

    @Test
    public void testTeacherLastName() {
        teacher.setLastName(lastName);

        assertEquals(lastName, teacher.getLastName());
    }

    @Test
    public void testTeacherFirstName() {
        teacher.setFirstName(firstName);

        assertEquals(firstName, teacher.getFirstName());
    }

}