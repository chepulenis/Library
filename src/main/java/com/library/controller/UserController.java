package com.library.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.library.domain.Book;
import com.library.domain.User;
import com.library.service.BookService;
import com.library.service.UserService;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;

    @GetMapping(value = "/users", produces = "application/json")
    public List<User> findAllUsers() {
        return userService.findAllUsers();
    }
    
    @GetMapping(value = "/users/{id}", produces = "application/json")
    public User findUserById(@PathVariable long id) {
        return userService.findUserById(id);
    }
    
    @PostMapping(value = "/users", produces = "application/json", consumes = "application/json")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }
    
    @PutMapping(value = "/users/{id}", produces = "application/json", consumes = "application/json")
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }
    
    @DeleteMapping (value = "/users/{id}", produces = "application/json")
    public void deleteUserById(@PathVariable long id) {
        userService.deleteUserById(id);
    }
    
    @PostMapping(value = "/users/{userId}/take-book/{bookId}", produces = "application/json", consumes = "application/json")
    public void takeBook(@PathVariable long userId, @PathVariable long bookId){
        User user = userService.findUserById(userId);
        Book book = bookService.findBookById(bookId);
        userService.takeBook(user, book);
    }
    
    @PostMapping(value = "/users/{userId}/return-book/{bookId}", produces = "application/json", consumes = "application/json")
    public void returnBook(@PathVariable long userId, @PathVariable long bookId){
        User user = userService.findUserById(userId);
        Book book = bookService.findBookById(bookId);
        userService.returnBook(user, book);
    }
    
}
