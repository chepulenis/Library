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
import com.library.service.BookService;

@RestController
public class BookController {

    @Autowired
    private BookService service;

    @GetMapping(value = "/books", produces = "application/json")
    public List<Book> findAllBooks() {
        return service.findAllBooks();
    }
    
    @GetMapping(value = "/books/{id}", produces = "application/json")
    public Book findBookById(@PathVariable long id) {
        return service.findBookById(id);
    }
    
    @PostMapping(value = "/books", produces = "application/json", consumes = "application/json")
    public Book createBook(@RequestBody Book book) {
        return service.createBook(book);
    }
    
    @PutMapping(value = "/books/{id}", produces = "application/json", consumes = "application/json")
    public Book updateBook(@RequestBody Book book) {
        return service.updateBook(book);
    }
    
    @DeleteMapping (value = "/books/{id}", produces = "application/json")
    public void deleteBookById(@PathVariable long id) {
        service.deleteBookById(id);
    }
    
}
