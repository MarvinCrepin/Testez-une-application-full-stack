package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    private UserService userService;

    private User createUser() {
        return new User(1l,"john@doe.com","Doe","John","john123",false, LocalDateTime.now(),LocalDateTime.now());
    }
    @BeforeEach
    void init(){
        userService = new UserService(userRepository);
    }

    @Test
    void delete() {
        userService.delete(any(Long.class));
        verify(userRepository).deleteById(any(Long.class));
    }

    @Test
    void findByIdWhenUserIsNotNull() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(this.createUser()));
        assertThat(userService.findById(10l)).isEqualTo(this.createUser());
        verify(userRepository).findById(any(Long.class));

    }

    @Test
    void findByIdWhenUserIsNull() {
        assertThat(userService.findById(any(Long.class))).isNull();
        verify(userRepository).findById(any(Long.class));

    }
}