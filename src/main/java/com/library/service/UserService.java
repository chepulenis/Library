package com.library.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.domain.Book;
import com.library.domain.User;
import com.library.repository.BookRepository;
import com.library.repository.UserRepository;

@Service
@Transactional
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    public User findUserById(long id) {
        User user = userRepository.findById(id).orElse(null);
        logger.info("{} by id {} has been found", user, id);
        return user;
    }

    public List<User> findAllUsers() {
        List<User> users = userRepository.findAll();
        logger.info("All users {} have been found", users);
        return users;
    }

    public User createUser(User user) {
        userRepository.save(user);
        logger.info("{} created", user);
        return user;
    }

    public User updateUser(User user) {
        userRepository.save(user);
        logger.info("{} updated", user);
        return user;
    }
    
    public void deleteUserById(long id) {
        userRepository.deleteById(id);
        logger.info("User by id {} has been deleted", id);
    }

    public void takeBook(User user, Book book) {
        if (!user.getBooks().contains(book) && !book.isTaken()) {
            user.getBooks().add(book);
            book.setTaken(true);
            book.setUser(user);
            userRepository.save(user);
            bookRepository.save(book);
            logger.info("{} has been taken by {}", book, user);
        }
    }
    
    public void returnBook(User user, Book book) {
        if (user.getBooks().contains(book)) {
            user.getBooks().remove(book);
            book.setTaken(false);
            book.setUser(null);
            userRepository.save(user);
            bookRepository.save(book);
            logger.info("{} has been returned by {}", book, user);
        }
    }

}
