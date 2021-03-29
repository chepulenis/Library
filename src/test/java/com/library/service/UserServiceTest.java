package com.library.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.junit.Test;

import com.library.domain.Book;
import com.library.domain.User;
import com.library.repository.BookRepository;
import com.library.repository.UserRepository;

public class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;
    @Mock
    BookRepository bookRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnAllUsers() {
        List<User> users = new ArrayList<User>();
        User jane = new User(1, "Jane", "Doe", 22, new ArrayList<Book>());
        User jack = new User(2, "Jack", "Jones", 28, new ArrayList<Book>());
        User thomas = new User(3, "Thomas", "Howard", 33, new ArrayList<Book>());
        users.add(jane);
        users.add(jack);
        users.add(thomas);

        when(userRepository.findAll()).thenReturn(users);

        List<User> userList = userService.findAllUsers();

        assertEquals(3, userList.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void shouldReturnUserById() {
        Optional<User> expected = Optional.of(new User(1, "Sean", "Turner", 33, new ArrayList<Book>()));
        when(userRepository.findById(1L)).thenReturn(expected);

        User actual = userService.findUserById(1L);

        assertEquals("Sean", actual.getFirstName());
        assertEquals("Turner", actual.getLastName());
        assertEquals(33, actual.getAge());
    }

    @Test
    public void shoudCreateUser() {
        User user = new User(1, "Jeam", "Beam", 33, new ArrayList<Book>());
        userService.createUser(user);
        verify(userRepository, times(1)).save(user);
    }
    
    @Test
    public void shoudTakeBook() {
        User user = new User(1, "John", "Doe", 30, new ArrayList<Book>());
        Book book = new Book(1, "Catcher in the Rye", "J. D. Salinger", "0-345-30443-2", false, new User());
        userService.takeBook(user, book);
        assertTrue(book.isTaken());
        assertEquals(user.getBooks().get(0), book);
        assertEquals(book.getUser(), user);
    }
    
    @Test
    public void shoudReturnBook() {
        User user = new User(1, "John", "Doe", 30, new ArrayList<Book>());
        Book book = new Book(1, "Catcher in the Rye", "J. D. Salinger", "0-345-30443-2", false, new User());
        userService.takeBook(user, book);
        userService.returnBook(user, book);
        assertFalse(book.isTaken());
        assertTrue(user.getBooks().isEmpty());
        assertNull(book.getUser());
    }

}
