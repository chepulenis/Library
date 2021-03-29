package com.library.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.domain.Book;
import com.library.repository.BookRepository;

@Service
@Transactional
public class BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    @Autowired
    private BookRepository repository;

    public Book findBookById(long id) {
        Book book = repository.findById(id).orElse(null);
        logger.info("Book {} by id {} has been found", book, id);
        return book;
    }

    public List<Book> findAllBooks() {
        List<Book> books = repository.findAll();
        logger.info("All books {} have been found", books);
        return books;
    }
    
    public Book createBook(Book book) {
        repository.save(book);
        logger.info("Book {} created", book);
        return book;
    }
    
    public Book updateBook(Book book) {
        repository.save(book);
        logger.info("Book {} updated", book);
        return book;
    }
    
    public void deleteBookById(long id) {
        repository.deleteById(id);
        logger.info("Book by id {} has been deleted", id);
    }
    
}
