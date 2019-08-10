package com.springfreamwork.thymeleafajax.app.controllers;

import com.springfreamwork.thymeleafajax.app.utility.EntityExistsException;
import com.springfreamwork.thymeleafajax.app.utility.NotFoundException;
import com.springfreamwork.thymeleafajax.domain.model.Author;
import com.springfreamwork.thymeleafajax.domain.model.Book;
import com.springfreamwork.thymeleafajax.domain.model.Genre;
import com.springfreamwork.thymeleafajax.domain.services.AuthorService;
import com.springfreamwork.thymeleafajax.domain.services.BookService;
import com.springfreamwork.thymeleafajax.domain.services.GenreService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.springfreamwork.thymeleafajax.domain.model.Country.RUSSIA;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void bookControllerShouldCallViewWithAllGenresAndFillModel() throws Exception {

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(view().name("allBooks"));
    }

    @Test
    public void bookControllerShouldReturnViewWithNameCreateGenre() throws Exception {

        mockMvc.perform(get("/createBook"))
                .andExpect(status().isOk())
                .andExpect(view().name("createBook"));
    }

    @Test
    public void bookControllerShouldReturnEditBookPageWithDataInModel() throws Exception {
        mockMvc.perform(get("/editBook"))
                .andExpect(status().isOk())
                .andExpect(view().name("editBook"));
    }

}