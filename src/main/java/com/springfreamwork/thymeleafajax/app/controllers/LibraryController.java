package com.springfreamwork.thymeleafajax.app.controllers;

import com.springfreamwork.thymeleafajax.domain.dto.LibraryDTO;
import com.springfreamwork.thymeleafajax.domain.services.AuthorService;
import com.springfreamwork.thymeleafajax.domain.services.BookService;
import com.springfreamwork.thymeleafajax.domain.services.CommentService;
import com.springfreamwork.thymeleafajax.domain.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LibraryController {

    private final GenreService genreService;
    private final AuthorService authorService;
    private final BookService bookService;
    private final CommentService commentService;

    @Autowired
    public LibraryController(
            GenreService genreService,
            AuthorService authorService,
            BookService bookService,
            CommentService commentService
    ) {
        this.genreService = genreService;
        this.authorService = authorService;
        this.bookService = bookService;
        this.commentService = commentService;
    }

    @GetMapping("/countObjects")
    public LibraryDTO welcomePage() {
        return new LibraryDTO(
                genreService.countGenres(),
                authorService.countAuthors(),
                bookService.countBooks(),
                commentService.countComments()
        );
    }
}
