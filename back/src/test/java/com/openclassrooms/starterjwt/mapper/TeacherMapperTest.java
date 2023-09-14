package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.models.Teacher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class TeacherMapperTest {
    @InjectMocks
    private TeacherMapper teacherMapper = Mappers.getMapper(TeacherMapper.class);

    @Test
    public void testToEntity() {
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setId(1L);
        teacherDto.setLastName("Doe");
        teacherDto.setFirstName("John");

        Teacher mappedTeacher = teacherMapper.toEntity(teacherDto);

        assertEquals(teacherDto.getLastName(), mappedTeacher.getLastName());
        assertEquals(teacherDto.getFirstName(), mappedTeacher.getFirstName());
    }

    @Test
    public void testToDto() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setLastName("Doe");
        teacher.setFirstName("John");

        TeacherDto teacherDto = teacherMapper.toDto(teacher);

        assertEquals(teacher.getLastName(), teacherDto.getLastName());
        assertEquals(teacher.getFirstName(), teacherDto.getFirstName());
    }

    @Test
    public void testToEntityList() {
        LocalDateTime date = LocalDateTime.now();
        List<TeacherDto> teacherDtoList = Arrays.asList(
                new TeacherDto(1l,"John","Doe",date,date),
                new TeacherDto(2L,"Smith", "Jane",date,date)
        );

        List<Teacher> mappedTeacherList = teacherMapper.toEntity(teacherDtoList);

        assertEquals(2, mappedTeacherList.size());
        assertEquals(teacherDtoList.get(0).getLastName(), mappedTeacherList.get(0).getLastName());
        assertEquals(teacherDtoList.get(0).getFirstName(), mappedTeacherList.get(0).getFirstName());
        assertEquals(teacherDtoList.get(1).getLastName(), mappedTeacherList.get(1).getLastName());
        assertEquals(teacherDtoList.get(1).getFirstName(), mappedTeacherList.get(1).getFirstName());
    }

    @Test
    public void testToDtoList() {
        LocalDateTime date = LocalDateTime.now();
        List<Teacher> teacherList = Arrays.asList(
                new Teacher(1L, "Doe", "John",date,date),
                new Teacher(2L, "Smith", "Jane",date,date)
        );

        List<TeacherDto> mappedTeacherDtoList = teacherMapper.toDto(teacherList);

        assertEquals(2, mappedTeacherDtoList.size());
        assertEquals(teacherList.get(0).getLastName(), mappedTeacherDtoList.get(0).getLastName());
        assertEquals(teacherList.get(0).getFirstName(), mappedTeacherDtoList.get(0).getFirstName());
        assertEquals(teacherList.get(1).getLastName(), mappedTeacherDtoList.get(1).getLastName());
        assertEquals(teacherList.get(1).getFirstName(), mappedTeacherDtoList.get(1).getFirstName());
    }

}