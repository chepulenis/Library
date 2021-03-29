package com.library.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.library.domain.Book;
import com.library.domain.User;
import com.library.repository.BookRepository;
import com.library.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {
    
    @Autowired
    private MockMvc mvc;

    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private UserRepository userRepository;

    @After
    public void resetDb() {
        bookRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void whenValidInputThenCreateBook() throws IOException, Exception {
        Book book = new Book(1, "Brave New World", "Aldous Huxley", "0-345-30443-9", false, null);
        mvc.perform(post("/books").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(book)));
        List<Book> found = bookRepository.findAll();
        assertThat(found).extracting(Book::getTitle).containsOnly("Brave New World");
    }

    @Test
    public void givenBooksWhenGetBooksThenStatus200() throws Exception {
        User user = new User(1, "Jane", "Doe", 22, new ArrayList<Book>());
        Book moby = new Book(2, "Moby Dick", "Herman Melville", "0-345-30447-9", true, user);
        Book odyssey = new Book(3, "The Odyssey", "Homer", "0-345-30447-8", true, user);
        bookRepository.saveAndFlush(moby);
        bookRepository.saveAndFlush(odyssey);
        userRepository.saveAndFlush(user);

        mvc.perform(get("/books").contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2)))).andExpect(jsonPath("$[0].id", is(2)))
                .andExpect(jsonPath("$[0].title", is("Moby Dick"))).andExpect(jsonPath("$[1].id", is(3)))
                .andExpect(jsonPath("$[1].title", is("The Odyssey")));
    }

}
