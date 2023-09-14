package com.openclassrooms.starterjwt.security.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class UserDetailsImplTest {

    private UserDetailsImpl userDetails;

    @BeforeEach
    public void setUp() {
        userDetails = UserDetailsImpl.builder()
                .id(1L)
                .username("testuser")
                .firstName("John")
                .lastName("Doe")
                .admin(false)
                .password("password")
                .build();
    }

    @Test
    public void testGetAuthorities() {
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        assertNotNull(authorities);
        assertTrue(authorities.isEmpty());
    }

    @Test
    public void testIsAccountNonExpired() {
        assertTrue(userDetails.isAccountNonExpired());
    }

    @Test
    public void testIsAccountNonLocked() {
        assertTrue(userDetails.isAccountNonLocked());
    }

    @Test
    public void testIsCredentialsNonExpired() {
        assertTrue(userDetails.isCredentialsNonExpired());
    }

    @Test
    public void testIsEnabled() {
        assertTrue(userDetails.isEnabled());
    }

    @Test
    public void testEquals() {
        UserDetailsImpl sameUser = UserDetailsImpl.builder()
                .id(1L)
                .username("testuser")
                .firstName("John")
                .lastName("Doe")
                .admin(false)
                .password("password")
                .build();

        UserDetailsImpl differentUser = UserDetailsImpl.builder()
                .id(2L)
                .username("anotheruser")
                .firstName("Jane")
                .lastName("Smith")
                .admin(true)
                .password("differentpassword")
                .build();

        assertEquals(userDetails, sameUser);
        assertNotEquals(userDetails, differentUser);
    }
}
