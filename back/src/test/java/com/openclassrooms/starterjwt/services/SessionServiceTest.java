package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SessionServiceTest {
    @Mock
    private SessionRepository sessionRepository;
    @Mock
    private UserRepository userRepository;

    private SessionService sessionService;
    private Session session;

    private User createUser() {
        return new User(1l,"john@doe.com","Doe","John","john123",false, LocalDateTime.now(),LocalDateTime.now());
    }
    @BeforeEach
    void setUp() {
        sessionService = new SessionService(sessionRepository, userRepository);
        List<User> userList = new ArrayList<>();
        userList.add(this.createUser());
        Teacher teacher = new Teacher(1l,"Dubois","Jean", LocalDateTime.now(), LocalDateTime.now());
        session = new Session(1l,"Midnight", new Date(),"Beginner", teacher, userList, LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    void create() {
        when(sessionRepository.save(any(Session.class))).thenReturn(session);
        assertThat(sessionService.create(session)).isEqualTo(session);
        verify(sessionRepository).save(any(Session.class));
    }

    @Test
    void delete() {
        sessionService.delete(any(Long.class));
        verify(sessionRepository).deleteById(any(Long.class));
    }

    @Test
    void findAll() {
        List<Session> sessions = new ArrayList<>();
        sessions.add(session);
        when(sessionRepository.findAll()).thenReturn(sessions);
        assertThat(sessionService.findAll()).hasSize(1);
        verify(sessionRepository).findAll();
    }

    @Test
    void getByIdIsNotNull() {
        when(sessionRepository.findById(any(Long.class))).thenReturn(Optional.of(session));
        assertThat(sessionService.getById(1l)).isEqualTo(session);
        verify(sessionRepository).findById(any(Long.class));
    }

    @Test
    void getByIdIsNull() {
        assertThat(sessionService.getById(any(Long.class))).isNull();
        verify(sessionRepository).findById(any(Long.class));
    }

    @Test
    void update() {
        when(sessionRepository.save(any(Session.class))).thenReturn(session);
        assertThat(sessionService.update(100l,session)).isEqualTo(session);
        verify(sessionRepository).save(any(Session.class));
    }

    @Test
    void participateWhenUserIsAddToUserList() {
        when(sessionRepository.findById(any(Long.class))).thenReturn(Optional.of(session));
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(this.createUser()));
        sessionService.participate(1l,20l);
        assertThat(session.getUsers()).hasSize(2);
        verify(sessionRepository).findById(any(Long.class));
        verify(userRepository).findById(any(Long.class));
        verify(sessionRepository).save(any(Session.class));
    }

}