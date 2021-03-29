package com.library.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.library.domain.Book;
import com.library.domain.User;
import com.library.repository.BookRepository;

public class BookServiceTest {

    @InjectMocks
    BookService service;

    @Mock
    BookRepository repository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnAllBooks() {

        List<Book> books = new ArrayList<Book>();
        Book harry = new Book(1, "Harry Potter and the Goblet of Fire", "J. K. Rowling", "0-345-30447-5", false,
                new User());
        Book lotr = new Book(2, "The Lord of the Rings", "J. R. R. Tolkien", "0-345-30447-8", false, new User());
        Book hamlet = new Book(3, "Hamlet", "William Shakespeare", "0-345-30447-1", false, new User());
        books.add(harry);
        books.add(lotr);
        books.add(hamlet);

        when(repository.findAll()).thenReturn(books);

        List<Book> bookList = service.findAllBooks();

        assertEquals(3, bookList.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void shouldReturnBookById() {
        Optional<Book> expected = Optional.of(new Book(1, "1984", "George Orwell", "0-345-30447-9", false, new User()));
        when(repository.findById(1L)).thenReturn(expected);

        Book actual = service.findBookById(1L);

        assertEquals("1984", actual.getTitle());
        assertEquals("George Orwell", actual.getAuthor());
        assertEquals("0-345-30447-9", actual.getISBN());
        assertEquals(false, actual.isTaken());
    }

    @Test
    public void shoudCreateBook() {
        Book book = new Book(1, "Brave New World", "Aldous Huxley", "0-345-30443-9", false, new User());
        service.createBook(book);
        verify(repository, times(1)).save(book);
    }

}
