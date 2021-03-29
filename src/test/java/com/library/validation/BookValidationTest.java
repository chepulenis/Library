package com.library.validation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

public class BookValidationTest {
   
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
        Book book = new Book(1, "Do Androids Dream of Electric Sheep?", "Philip K. Dick", "0-345-40447-5", false, new User());
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertTrue(violations.isEmpty());
    }
    
    @Test
    public void shouldDetectShortTitle() throws Exception {
        Book book = new Book(1, "a", "Philip K. Dick", "0-345-40447-5", false, new User());
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertEquals(violations.size(), 1);
        ConstraintViolation<Book> violation = violations.iterator().next();
        assertEquals("size must be between 2 and 80", violation.getMessage());
        assertEquals("title", violation.getPropertyPath().toString());
        assertEquals("a", violation.getInvalidValue());
    }
    
    @Test
    public void shouldDetectLongTitle() throws Exception {
        Book book = new Book(1, "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor.", 
                "Philip K. Dick", "0-345-40447-5", false, new User());
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertEquals(violations.size(), 1);
        ConstraintViolation<Book> violation = violations.iterator().next();
        assertEquals("size must be between 2 and 80", violation.getMessage());
        assertEquals("title", violation.getPropertyPath().toString());
        assertEquals("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor.", violation.getInvalidValue());
    }
    
    @Test
    public void shouldDetectShortAuthor() throws Exception {
        Book book = new Book(1, "Do Androids Dream of Electric Sheep?", "a", "0-345-40447-5", false, new User());
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertEquals(violations.size(), 1);
        ConstraintViolation<Book> violation = violations.iterator().next();
        assertEquals("size must be between 2 and 80", violation.getMessage());
        assertEquals("author", violation.getPropertyPath().toString());
        assertEquals("a", violation.getInvalidValue());
    }
    
    @Test
    public void shouldDetectLongAuthor() throws Exception {
        Book book = new Book(1, "Do Androids Dream of Electric Sheep?", 
                "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor.", "0-345-40447-5", false, new User());
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertEquals(violations.size(), 1);
        ConstraintViolation<Book> violation = violations.iterator().next();
        assertEquals("size must be between 2 and 80", violation.getMessage());
        assertEquals("author", violation.getPropertyPath().toString());
        assertEquals("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor.", violation.getInvalidValue());
    }
    
    @Test
    public void shouldDetectIncorrectISBN() throws Exception {
        Book book = new Book(1, "Do Androids Dream of Electric Sheep?", "Philip K. Dick", "7777", false, new User());
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertEquals(violations.size(), 1);
        ConstraintViolation<Book> violation = violations.iterator().next();
        assertEquals("must match \"^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})"
                + "[- 0-9]{17}$)(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$\"", violation.getMessage());
        assertEquals("ISBN", violation.getPropertyPath().toString());
        assertEquals("7777", violation.getInvalidValue());
    }
    
}
