package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.repository.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeacherServiceTest {
    @Mock
    private TeacherRepository teacherRepository;

    private TeacherService teacherService;
    private Teacher teacher;

    @BeforeEach
    void setUp() {
        teacherService = new TeacherService(teacherRepository);
        teacher = new Teacher(1l,"Dubois","Jean", LocalDateTime.now(),LocalDateTime.now());
    }

    @Test
    void findAll() {
        List<Teacher> teachers = new ArrayList<>();
        teachers.add(teacher);
        when(teacherRepository.findAll()).thenReturn(teachers);
        assertThat(teacherService.findAll()).isEqualTo(teachers);
        verify(teacherRepository).findAll();
    }

    @Test
    void findByIdWhenIsNotNull() {
        when(teacherRepository.findById(any(Long.class))).thenReturn(Optional.of(teacher));
        assertThat(teacherService.findById(any(Long.class))).isEqualTo(teacher);
        verify(teacherRepository).findById(any(Long.class));
    }

    @Test
    void findByIdWhenIsNull() {
        assertThat(teacherService.findById(any(Long.class))).isNull();
        verify(teacherRepository).findById(any(Long.class));
    }
}