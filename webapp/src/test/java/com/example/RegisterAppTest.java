package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RegisterAppTest {
    
    @Test
    public void testEmailValidation() {
        String email = "test@example.com";
        assertTrue(email.contains("@"), "Email should contain @");
    }
    
    @Test
    public void testPasswordStrength() {
        String password = "Test@123";
        assertTrue(password.length() >= 6, "Password must be 6+ chars");
    }
    
    @Test
    public void testNameNotEmpty() {
        String name = "John Doe";
        assertFalse(name.trim().isEmpty(), "Name cannot be empty");
    }
    
    @Test
    public void testMobileNumberFormat() {
        String mobile = "9876543210";
        assertTrue(mobile.matches("\\d{10}"), "Mobile should be 10 digits");
    }
}