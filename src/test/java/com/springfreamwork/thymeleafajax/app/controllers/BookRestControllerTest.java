package com.springfreamwork.thymeleafajax.app.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springfreamwork.thymeleafajax.app.utility.EntityExistsException;
import com.springfreamwork.thymeleafajax.app.utility.NotFoundException;
import com.springfreamwork.thymeleafajax.domain.dto.BookCreateDTO;
import com.springfreamwork.thymeleafajax.domain.dto.BookDTO;
import com.springfreamwork.thymeleafajax.domain.dto.BookDataDTO;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.springfreamwork.thymeleafajax.domain.model.Country.RUSSIA;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(BookRestController.class)
public class BookRestControllerTest {

    private Genre genre = new Genre("genre_id", "genre_name");
    private Author author = new Author("author_id", "author_name", "author_surname", RUSSIA);
    private Book book = new Book("book_id", "book_name", new Date(), 1, Collections.emptyMap(), Collections.singleton(author), genre);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GenreService genreService;
    @MockBean
    private AuthorService authorService;
    @MockBean
    private BookService bookService;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void bookControllerShouldDeleteGenreAndRedirectToAllGenresView() throws Exception {
        mockMvc.perform(delete("/deleteBook")
                .param("id", book.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("success"));
    }

    @Test
    public void bookControllerShouldCallViewWithAllGenresAndFillModel() throws Exception {
        when(bookService.getAllBooks()).thenReturn(Collections.singletonList(book));

        String books = mapper.writeValueAsString(Collections.singleton(BookDTO.getBookDTO(book)));

        mockMvc.perform(get("/getBooks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(books));

        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    public void bookControllerShouldReturnBookDTO() throws Exception{
        when(bookService.getBook(eq(book.getId()))).thenReturn(book);
        when(authorService.getAllAuthors()).thenReturn(Collections.singletonList(author));
        when(genreService.getAllGenres()).thenReturn(Collections.singletonList(genre));

        String bookDTO = mapper.writeValueAsString(BookDataDTO.getBookDataDTO(book, Collections.singletonList(author), Collections.singletonList(genre)));

        mockMvc.perform(get("/getBook").param("id", book.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(bookDTO));
    }

    @Test
    public void bookControllerShouldUpdateGenreInDbAndRedirectToAllGenresView() throws Exception {
        BookCreateDTO bookCreateDTO = new BookCreateDTO();
        bookCreateDTO.setId(book.getId());
        bookCreateDTO.setName(book.getName());
        bookCreateDTO.setDate(book.getPublishedDate());
        bookCreateDTO.setAuthors(book.getAuthors().stream().map(Author::getId).collect(Collectors.toSet()));
        bookCreateDTO.setGenre(book.getGenre().getId());

        when(authorService.getAuthorsById(eq(bookCreateDTO.getAuthors()))).thenReturn(Collections.singleton(author));
        when(genreService.getGenreById(eq(bookCreateDTO.getGenre()))).thenReturn(genre);

        String bookCreateDTOString = mapper.writeValueAsString(bookCreateDTO);

        mockMvc.perform(post("/editBook")
                .content(bookCreateDTOString)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(status().isOk())
                .andExpect(content().string("success"));

        verify(bookService, times(1)).updateBook(any());
        verify(authorService, times(1)).getAuthorsById(eq(bookCreateDTO.getAuthors()));
        verify(genreService, times(1)).getGenreById(eq(bookCreateDTO.getGenre()));
    }

    @Test
    public void getBookData() throws Exception{
        when(authorService.getAllAuthors()).thenReturn(Collections.singletonList(author));
        when(genreService.getAllGenres()).thenReturn(Collections.singletonList(genre));

        BookDataDTO bookDataDTO = BookDataDTO.getBookDataDTO(null, Collections.singletonList(author), Collections.singletonList(genre));
        String bookDataDTOString = mapper.writeValueAsString(bookDataDTO);
        mockMvc.perform(get("/getBookData"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(bookDataDTOString));

        verify(authorService, times(1)).getAllAuthors();
        verify(genreService, times(1)).getAllGenres();
    }

    @Test
    public void bookControllerShouldCreateGenreOnPostMethodAndRedirectToAllGenresView() throws Exception {
        BookCreateDTO bookCreateDTO = new BookCreateDTO();
        bookCreateDTO.setId(book.getId());
        bookCreateDTO.setName(book.getName());
        bookCreateDTO.setDate(book.getPublishedDate());
        bookCreateDTO.setAuthors(book.getAuthors().stream().map(Author::getId).collect(Collectors.toSet()));
        bookCreateDTO.setGenre(book.getGenre().getId());

        String bookCreateDTOString = mapper.writeValueAsString(bookCreateDTO);

        mockMvc.perform(post("/createBook")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(bookCreateDTOString))
                .andExpect(status().isOk())
                .andExpect(content().string("success"));

        verify(authorService, times(1)).getAuthorsById(eq(bookCreateDTO.getAuthors()));
        verify(genreService, times(1)).getGenreById(eq(bookCreateDTO.getGenre()));
        verify(bookService, times(1)).createBook(any());
    }
}
