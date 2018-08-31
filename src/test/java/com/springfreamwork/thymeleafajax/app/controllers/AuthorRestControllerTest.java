package com.springfreamwork.thymeleafajax.app.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springfreamwork.thymeleafajax.domain.dto.AuthorDTO;
import com.springfreamwork.thymeleafajax.domain.model.Author;
import com.springfreamwork.thymeleafajax.domain.model.Country;
import com.springfreamwork.thymeleafajax.domain.services.AuthorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static com.springfreamwork.thymeleafajax.domain.model.Country.RUSSIA;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthorRestController.class)
public class AuthorRestControllerTest {

    private Author author = new Author("author_id", "author_name", "author_surname", RUSSIA);
    private ObjectMapper mapper = new ObjectMapper();

    @MockBean
    private AuthorService authorService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void authorControllerShouldCallViewWithAllGenresAndFillModel() throws Exception {
        when(authorService.getAllAuthors()).thenReturn(Collections.singletonList(author));
        String authors = mapper.writeValueAsString(Collections.singleton(author));

        mockMvc.perform(get("/getAuthors"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(authors));

        verify(authorService, times(1)).getAllAuthors();
    }

    @Test
    public void authorControllerShouldDeleteGenreAndRedirectToAllGenresView() throws Exception {
        mockMvc.perform(delete("/deleteAuthor")
                .param("id", author.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("success"));

        verify(authorService, times(1)).deleteAuthor(eq(author.getId()));
    }

    @Test
    public void authorControllerShouldUpdateGenreInDbAndRedirectToAllGenresView() throws Exception {
        when(authorService.getAuthorById(eq(author.getId()))).thenReturn(author);

        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(author.getId());
        authorDTO.setName(author.getName());
        authorDTO.setSurname(authorDTO.getSurname());
        authorDTO.setCountry(authorDTO.getCountry());

        String authorDTOString = mapper.writeValueAsString(authorDTO);

        mockMvc.perform(post("/editAuthor")
                .content(authorDTOString)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(status().isOk())
        .andExpect(content().string("success"));

        verify(authorService, times(1)).getAuthorById(eq(author.getId()));
        verify(authorService, times(1)).updateAuthor(author);
    }

    @Test
    public void authorControllerShouldReturnCountries() throws Exception {
        String countries = mapper.writeValueAsString(Arrays.asList(Country.values()));
        mockMvc.perform(get("/getCountries"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(countries));
    }

    @Test
    public void authorControllerShouldReturnViewWithNameCreateGenre() throws Exception {
        Author author = new Author(this.author.getName(), this.author.getSurname(), this.author.getCountry());

        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(null);
        authorDTO.setName(author.getName());
        authorDTO.setSurname(author.getSurname());
        authorDTO.setCountry(author.getCountry());

        String authorDTOString = mapper.writeValueAsString(authorDTO);

        mockMvc.perform(post("/createAuthor")
                .content(authorDTOString)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(status().isOk())
                .andExpect(content().string("success"));

        verify(authorService, times(1)).createAuthor(author);
    }
}
