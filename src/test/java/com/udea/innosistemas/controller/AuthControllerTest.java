package com.udea.innosistemas.controller;


import com.udea.innosistemas.dto.AuthResponse;
import com.udea.innosistemas.dto.LoginRequest;
import com.udea.innosistemas.dto.UserInfo;
import com.udea.innosistemas.entity.UserRole;
import com.udea.innosistemas.service.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class AuthControllerTest {

    @Autowired
    private AuthController authController;

    @MockBean
    private AuthenticationService authenticationService;

    // Prueba 1: Login exitoso
    @Test
    void login_Successful_ReturnsAuthResponse() {
        // Arrange - preparar datos de prueba
        LoginRequest loginRequest = new LoginRequest("test@udea.edu.co", "password123");

        UserInfo userInfo = new UserInfo();
        userInfo.setId(1L);
        userInfo.setEmail("test@udea.edu.co");
        userInfo.setRole(UserRole.STUDENT);
        userInfo.setFullName("Test User");

        AuthResponse mockResponse = new AuthResponse();
        mockResponse.setToken("jwt-token");
        mockResponse.setUserInfo(userInfo);

        when(authenticationService.login(any(LoginRequest.class))).thenReturn(mockResponse);

        // Act - ejecutar el método
        ResponseEntity<AuthResponse> response = authController.login(loginRequest);

        // Assert - verificar resultados
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("jwt-token", response.getBody().getToken());
        assertEquals("test@udea.edu.co", response.getBody().getUserInfo().getEmail());
    }

    // Prueba 2: Health check básico
    @Test
    void health_ReturnsServiceStatus() {
        // Act
        ResponseEntity<String> response = authController.health();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Auth service is running", response.getBody());
    }

    // Prueba 3: Health check performance (opcional)
    @Test
    void health_Performance_ReturnsQuickly() {
        long startTime = System.currentTimeMillis();

        authController.health();

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        assertTrue(executionTime < 1000); // Menos de 1 segundo
    }
}
