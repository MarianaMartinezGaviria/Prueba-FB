package com.udea.innosistemas.service;

import com.udea.innosistemas.entity.User;
import com.udea.innosistemas.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    // Prueba 1: Usuario encontrado exitosamente
    @Test
    void loadUserByUsername_UserExists_ReturnsUserDetails() {
        // Arrange
        String email = "usuario@udea.edu.co";
        User user = new User();
        user.setEmail(email);
        user.setPassword("password123");

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // Act
        UserDetails result = userDetailsService.loadUserByUsername(email);

        // Assert
        assertNotNull(result);
        assertEquals(email, result.getUsername());
        assertEquals(user, result); // User implementa UserDetails
    }

    // Prueba 2: Usuario no encontrado
    @Test
    void loadUserByUsername_UserNotFound_ThrowsException() {
        // Arrange
        String email = "noexiste@udea.edu.co";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act & Assert
        UsernameNotFoundException exception = assertThrows(
                UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername(email)
        );

        assertEquals("Usuario no encontrado con email: " + email, exception.getMessage());
    }

    // Prueba 3: Email nulo o vacío
    @Test
    void loadUserByUsername_EmptyEmail_ThrowsException() {
        // Arrange
        when(userRepository.findByEmail("")).thenReturn(Optional.empty());

        // Act & Assert
        UsernameNotFoundException exception = assertThrows(
                UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername("")
        );

        assertEquals("Usuario no encontrado con email: ", exception.getMessage());
    }
}