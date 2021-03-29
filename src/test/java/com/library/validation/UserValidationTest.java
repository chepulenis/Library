package com.library.validation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.library.domain.Book;
import com.library.domain.User;

public class UserValidationTest {
    
    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeClass
    public static void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterClass
    public static void close() {
        validatorFactory.close();
    }

    @Test
    public void shouldHaveNoViolations() throws Exception {
        User user = new User(1, "Robert", "Johnson", 33, new ArrayList<Book>());
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }
    
    @Test
    public void shouldDetectShortFirstName() throws Exception {
        User user = new User(1, "a", "Johnson", 33, new ArrayList<Book>());
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(violations.size(), 1);
        ConstraintViolation<User> violation = violations.iterator().next();
        assertEquals("size must be between 2 and 40", violation.getMessage());
        assertEquals("firstName", violation.getPropertyPath().toString());
        assertEquals("a", violation.getInvalidValue());
    }
    
    @Test
    public void shouldDetectLongFirstName() throws Exception {
        User user = new User(1, "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "Johnson", 33, new ArrayList<Book>());
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(violations.size(), 1);
        ConstraintViolation<User> violation = violations.iterator().next();
        assertEquals("size must be between 2 and 40", violation.getMessage());
        assertEquals("firstName", violation.getPropertyPath().toString());
        assertEquals("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", violation.getInvalidValue());
    }
    
    @Test
    public void shouldDetectShortLastName() throws Exception {
        User user = new User(1, "Robert", "a", 33, new ArrayList<Book>());
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(violations.size(), 1);
        ConstraintViolation<User> violation = violations.iterator().next();
        assertEquals("size must be between 2 and 40", violation.getMessage());
        assertEquals("lastName", violation.getPropertyPath().toString());
        assertEquals("a", violation.getInvalidValue());
    }
    
    @Test
    public void shouldDetectLongLastName() throws Exception {
        User user = new User(1, "Robert", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", 33, new ArrayList<Book>());
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(violations.size(), 1);
        ConstraintViolation<User> violation = violations.iterator().next();
        assertEquals("size must be between 2 and 40", violation.getMessage());
        assertEquals("lastName", violation.getPropertyPath().toString());
        assertEquals("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", violation.getInvalidValue());
    }
    
    @Test
    public void shouldDetectNegativeAge() throws Exception {
        User user = new User(1, "Robert", "Johnson", -33, new ArrayList<Book>());
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(violations.size(), 1);
        ConstraintViolation<User> violation = violations.iterator().next();
        assertEquals("must be greater than 0", violation.getMessage());
        assertEquals("age", violation.getPropertyPath().toString());
        assertEquals(-33, violation.getInvalidValue());
    }
    
    @Test
    public void shouldLargeAge() throws Exception {
        User user = new User(1, "Robert", "Johnson", 3333, new ArrayList<Book>());
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(violations.size(), 1);
        ConstraintViolation<User> violation = violations.iterator().next();
        assertEquals("must be less than or equal to 150", violation.getMessage()); 
        assertEquals("age", violation.getPropertyPath().toString());
        assertEquals(3333, violation.getInvalidValue());
    }

}
