package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RegisterAppTest {
    
    @Test
    public void testEmailValidation() {
        // Test if email is valid
        String email = "test@example.com";
        assertTrue(email.contains("@"), "Email should contain @");
    }
    
    @Test
    public void testPasswordStrength() {
        // Test if password is strong
        String password = "Test@123";
        assertTrue(password.length() >= 6, "Password must be 6+ chars");
    }
    
    @Test
    public void testNameNotEmpty() {
        // Test if name is not empty
        String name = "John Doe";
        assertFalse(name.trim().isEmpty(), "Name cannot be empty");
    }
    
    @Test
    public void testMobileNumberFormat() {
        // Test if mobile is 10 digits
        String mobile = "9876543210";
        assertTrue(mobile.matches("\\d{10}"), "Mobile should be 10 digits");
    }
}