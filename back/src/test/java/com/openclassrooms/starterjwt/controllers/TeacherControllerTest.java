package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.models.Teacher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.mapper.TeacherMapper;
import com.openclassrooms.starterjwt.services.TeacherService;

@ExtendWith(MockitoExtension.class)
class TeacherControllerTest {

    @Mock
    private TeacherMapper teacherMapper;

    @Mock
    private TeacherService teacherService;

    @Test
    public void testTeacherFindByIdOK() {
        Long id = 1L;
        String name = "John Doe";
        Teacher teacher = Teacher.builder().id(id).lastName("Doe").build();

        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setId(id);
        teacherDto.setLastName(name);

        when(teacherService.findById(id)).thenReturn(teacher);
        when(teacherMapper.toDto(teacher)).thenReturn(teacherDto);
        TeacherController teacherController = new TeacherController(teacherService, teacherMapper);
        ResponseEntity<?> response = teacherController.findById(id.toString());

        verify(teacherService).findById(id);
        verify(teacherMapper).toDto(teacher);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody(), teacherDto);
    }

    @Test
    public void findTeacherByNotFoundId() {
        Long id = 1L;

        when(teacherService.findById(id)).thenReturn(null);
        TeacherController teacherController = new TeacherController(teacherService, teacherMapper);
        ResponseEntity<?> response = teacherController.findById(id.toString());

        verify(teacherService).findById(id);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(response.getBody(), null);

    }

    @Test
    public void findTeacherWithBadId() {
        TeacherController teacherController = new TeacherController(teacherService, teacherMapper);
        ResponseEntity<?> response = teacherController.findById("Bad ID");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void findAllTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        teachers.add(new Teacher());
        teachers.add(new Teacher());
        teachers.add(new Teacher());

        List<TeacherDto> teacherDtos = new ArrayList<>();
        teacherDtos.add(new TeacherDto());
        teacherDtos.add(new TeacherDto());
        teacherDtos.add(new TeacherDto());

        when(teacherService.findAll()).thenReturn(teachers);
        when(teacherMapper.toDto(teachers)).thenReturn(teacherDtos);
        TeacherController teacherController = new TeacherController(teacherService, teacherMapper);
        ResponseEntity<?> responseEntity = teacherController.findAll();

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(teacherDtos, responseEntity.getBody());
    }

}