package com.udea.innosistemas.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class LoginRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    // Prueba 1: Datos válidos
    @Test
    void loginRequest_WithValidData_NoValidationErrors() {
        LoginRequest loginRequest = new LoginRequest("usuario@udea.edu.co", "password123");
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);
        assertTrue(violations.isEmpty());
    }

    // Prueba 2: Email inválido
    @Test
    void loginRequest_WithInvalidEmail_ValidationFails() {
        LoginRequest loginRequest = new LoginRequest("email-invalido", "password123");
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);
        assertFalse(violations.isEmpty());
    }

    // Prueba 3: Password vacío
    @Test
    void loginRequest_WithEmptyPassword_ValidationFails() {
        LoginRequest loginRequest = new LoginRequest("usuario@udea.edu.co", "");
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);
        assertFalse(violations.isEmpty());
    }
}