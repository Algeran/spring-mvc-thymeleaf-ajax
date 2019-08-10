package com.springfreamwork.thymeleafajax.app.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springfreamwork.thymeleafajax.domain.dto.CommentDTO;
import com.springfreamwork.thymeleafajax.domain.model.Author;
import com.springfreamwork.thymeleafajax.domain.model.Book;
import com.springfreamwork.thymeleafajax.domain.model.Comment;
import com.springfreamwork.thymeleafajax.domain.model.Genre;
import com.springfreamwork.thymeleafajax.domain.services.BookService;
import com.springfreamwork.thymeleafajax.domain.services.CommentService;
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

import static com.springfreamwork.thymeleafajax.domain.model.Country.RUSSIA;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CommentRestController.class)
public class CommentRestControllerTest {


    private Genre genre = new Genre("genre_id", "genre_name");
    private Author author = new Author("author_id", "author_name", "author_surname", RUSSIA);
    private Book book = new Book("book_id", "book_name", new Date(), 1, Collections.emptyMap(), Collections.singleton(author), genre);
    private Comment comment = new Comment("comment_id", "username", "comment", Collections.singleton(book));

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private CommentService commentService;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void commentControllerShouldCallViewWithAllGenresAndFillModel() throws Exception {
        when(commentService.getAllComments()).thenReturn(Collections.singletonList(comment));

        String content = mapper.writeValueAsString(Collections.singletonList(CommentDTO.getCommentDTO(comment)));

        mockMvc.perform(get("/getComments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(content));

        verify(commentService, times(1)).getAllComments();
    }

    @Test
    public void commentControllerShouldDeleteGenreAndRedirectToAllGenresView() throws Exception {
        mockMvc.perform(delete("/deleteComment")
                .param("id", comment.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("success"));
    }
}
