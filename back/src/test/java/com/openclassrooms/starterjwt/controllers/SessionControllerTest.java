package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.mapper.SessionMapper;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.payload.response.JwtResponse;
import com.openclassrooms.starterjwt.services.SessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class SessionControllerTest {
    @Mock
    private SessionMapper sessionMapper ;
    @Mock
    private SessionService sessionService;
    private Session session;
    private SessionDto sessionDto;

    @BeforeEach
    void init(){
        User user= new User(10l,"john@doe.com","Doe","John","johndoe123",false, LocalDateTime.now(),LocalDateTime.now());
        List<User> userList = new ArrayList<>();
        userList.add(user);
        Teacher teacher = new Teacher(1l,"Jean","Dubois", LocalDateTime.now(),LocalDateTime.now());
        session = new Session(1l,"Beginner",new Date(),"Easy level", teacher, userList, LocalDateTime.now(),LocalDateTime.now());
        List<Long> usersIdList = new ArrayList<>();
        usersIdList.add(10l);
        sessionDto = new SessionDto(1l,"Beginner", new Date(),1L,"Easy level", usersIdList, LocalDateTime.now(), LocalDateTime.now());
    }


    @Test
    void findById() throws Exception{
        when(sessionService.getById(any(Long.class))).thenReturn(session);
        when(sessionMapper.toDto(any(Session.class))).thenReturn(sessionDto);
        SessionController sessionController = new SessionController(sessionService,sessionMapper);
        ResponseEntity<?> response = sessionController.findById("1");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sessionDto,response.getBody());
        verify(sessionService).getById(any(Long.class));
        verify(sessionMapper).toDto(any(Session.class));
    }

    @Test
    void findAll() {
        List<Session> sessionList = new ArrayList<>();
        sessionList.add(session);
        List<SessionDto> sessionDtoList = new ArrayList<>();
        sessionDtoList.add(sessionDto);
        when(sessionService.findAll()).thenReturn(sessionList);
        when(sessionMapper.toDto(sessionList)).thenReturn(sessionDtoList);
        SessionController sessionController = new SessionController(sessionService,sessionMapper);
        ResponseEntity<?> response = sessionController.findAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sessionDtoList,response.getBody());
        verify(sessionService).findAll();
        verify(sessionMapper).toDto(sessionList);
    }

    @Test
    void create() {
        when(sessionMapper.toEntity(sessionDto)).thenReturn(session);
        when(sessionService.create(any(Session.class))).thenReturn(session);
        when(sessionMapper.toDto(any(Session.class))).thenReturn(sessionDto);
        SessionController sessionController = new SessionController(sessionService,sessionMapper);
        ResponseEntity<?> response = sessionController.create(sessionDto);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(sessionDto, response.getBody());
        verify(sessionMapper).toEntity(any(SessionDto.class));
        verify(sessionService).create(any(Session.class));
        verify(sessionMapper).toDto(any(Session.class));
    }

    @Test
    void update() {
        when(sessionMapper.toEntity(sessionDto)).thenReturn(session);
        when(sessionService.update(any(Long.class),any(Session.class))).thenReturn(session);
        when(sessionMapper.toDto(any(Session.class))).thenReturn(sessionDto);
        SessionController sessionController = new SessionController(sessionService,sessionMapper);
        ResponseEntity<?> response = sessionController.update("1",sessionDto);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(sessionDto, response.getBody());
        verify(sessionMapper).toEntity(any(SessionDto.class));
        verify(sessionService).update(any(Long.class),any(Session.class));
        verify(sessionMapper).toDto(any(Session.class));
    }

    @Test
    void save() {
        when(sessionService.getById(any(Long.class))).thenReturn(session);
        SessionController sessionController = new SessionController(sessionService,sessionMapper);
        ResponseEntity<?> response = sessionController.save("1");
        assertEquals(HttpStatus.OK,response.getStatusCode());
        verify(sessionService).getById(any(Long.class));
        verify(sessionService).delete(any(Long.class));
    }

    @Test
    void participate() {
        SessionController sessionController = new SessionController(sessionService,sessionMapper);
        ResponseEntity<?> response = sessionController.participate("1","10");
        assertEquals(HttpStatus.OK,response.getStatusCode());
        verify(sessionService).participate(any(Long.class),any(Long.class));
    }

    @Test
    void noLongerParticipate() {
        SessionController sessionController = new SessionController(sessionService,sessionMapper);
        ResponseEntity<?> response = sessionController.noLongerParticipate("1","10");
        assertEquals(HttpStatus.OK,response.getStatusCode());
        verify(sessionService).noLongerParticipate(any(Long.class),any(Long.class));
    }
}