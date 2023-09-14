package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.TeacherService;
import com.openclassrooms.starterjwt.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SessionMapperTest {
    @Mock
    private TeacherService teacherService;

    @Mock
    private UserService userService;

    @InjectMocks
    private SessionMapper sessionMapper = Mappers.getMapper(SessionMapper.class);

    @Test
    public void dtoToEntity() {
        SessionDto sessionDto = new SessionDto();
        sessionDto.setName("Test Session");
        sessionDto.setDescription("Test description");
        sessionDto.setTeacher_id(1L);
        sessionDto.setUsers(Collections.singletonList(2L));

        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("John");
        teacher.setLastName("Doe");

        User user = new User();
        user.setId(2L);
        user.setFirstName("Jane");
        user.setLastName("Smith");

        when(teacherService.findById(1L)).thenReturn(teacher);
        when(userService.findById(2L)).thenReturn(user);

        Session session = sessionMapper.toEntity(sessionDto);

        assertEquals(sessionDto.getName(), session.getName());
        assertEquals(sessionDto.getDescription(), session.getDescription());
        assertEquals(teacher, session.getTeacher());
        assertEquals(1, session.getUsers().size());
        assertEquals(user, session.getUsers().get(0));
    }
    @Test
    public void entityWhenSessionDtoIsNull(){
        SessionDto sessionDto = null;
        assertNull(sessionMapper.toEntity(sessionDto));
    }
    @Test
    public void dtoWhenSessionIsNull(){
        assertNull(sessionMapper.toDto((Session) null));
    }
    @Test
    public void entityListWhenSessionDtoIsNull(){
        List<SessionDto> sessionDtos = null;
        assertNull(sessionMapper.toEntity((List<SessionDto>) null));
    }
    @Test
    public void dtoListWhenSessionIsNull(){
        assertNull(sessionMapper.toDto((List<Session>) null));
    }

    @Test
    public void dto() {
        Session session = new Session();
        session.setName("Test Session");
        session.setDescription("Test description");

        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("John");
        teacher.setLastName("Doe");

        User user = new User();
        user.setId(2L);
        user.setFirstName("Jane");
        user.setLastName("Smith");

        session.setTeacher(teacher);
        session.setUsers(Collections.singletonList(user));

        SessionDto sessionDto = sessionMapper.toDto(session);

        assertEquals(session.getName(), sessionDto.getName());
        assertEquals(session.getDescription(), sessionDto.getDescription());
        assertEquals(1L, sessionDto.getTeacher_id());
        assertEquals(1, sessionDto.getUsers().size());
        assertEquals(2L, sessionDto.getUsers().get(0).longValue());
    }
    @Test
    void entityList() {
        LocalDateTime date = LocalDateTime.now();

        Teacher teacher1 = new Teacher(1l,"John","Doe",date,date);
        Teacher teacher2 = new Teacher(2L,"Smith", "Jane",date,date);

        User user1 = new User(3l,"john.doe@test.com","John","Doe","password",false,date,date);
        User user2 = new User(4l,"julia.doe@test.com","julia","Doe","password",false,date,date);

        List<SessionDto> sessionDtoList = Arrays.asList(
                new SessionDto(1L,"Session1",new Date(),1L,"Test Description",Arrays.asList(3L, 4L),date,date),
                new SessionDto(2L,"Session2",new Date(),2L,"Test Description",Arrays.asList(3L, 4L),date,date)
        );



        when(teacherService.findById(1L)).thenReturn(teacher1);
        when(teacherService.findById(2L)).thenReturn(teacher2);
        when(userService.findById(3L)).thenReturn(user1);
        when(userService.findById(4L)).thenReturn(user2);

        List<Session> mappedSessionList = sessionMapper.toEntity(sessionDtoList);

        assertEquals(2, mappedSessionList.size());

        Session session1 = mappedSessionList.get(0);
        assertEquals("Session1", session1.getName());
        assertEquals("Test Description", session1.getDescription());
        assertEquals(teacher1, session1.getTeacher());
        assertEquals(2, session1.getUsers().size());
        assertEquals(user1, session1.getUsers().get(0));
        assertEquals(user2, session1.getUsers().get(1));
    }

    @Test
    public void dtoList() {
        LocalDateTime date = LocalDateTime.now();
        Teacher teacher1 = new Teacher(1l,"John","Doe",date,date);
        Teacher teacher2 = new Teacher(2L,"Smith", "Jane",date,date);

        User user1 = new User(3l,"john.doe@test.com","John","Doe","password",false,date,date);
        User user2 = new User(4l,"julia.doe@test.com","julia","Doe","password",false,date,date);
        List<User> userList = Arrays.asList(user1,user2);

        List<Session> sessionList = Arrays.asList(
                new Session(1L,"Session1",new Date(),"Test Description",teacher1,userList,date,date),
                new Session(2L,"Session2",new Date(),"Test Description",teacher2,userList,date,date)
        );

        List<SessionDto> mappedSessionDtoList = sessionMapper.toDto(sessionList);


        assertEquals(2, mappedSessionDtoList.size());

        SessionDto sessionDto1 = mappedSessionDtoList.get(0);
        assertEquals("Session1", sessionDto1.getName());
        assertEquals("Test Description", sessionDto1.getDescription());
        assertEquals(1L, sessionDto1.getTeacher_id());
        assertEquals(2, sessionDto1.getUsers().size());
        assertEquals(3L, sessionDto1.getUsers().get(0).longValue());
        assertEquals(4L, sessionDto1.getUsers().get(1).longValue());

    }

}