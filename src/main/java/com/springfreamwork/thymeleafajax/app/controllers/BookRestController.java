package com.springfreamwork.thymeleafajax.app.controllers;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class BookRestController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    public BookRestController(
            BookService bookService,
            AuthorService authorService,
            GenreService genreService
    ) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @DeleteMapping("/deleteBook")
    public String deleteBook(@RequestParam("id") String id) {
        bookService.deleteBook(id);
        return "success";
    }

    @GetMapping("/getBooks")
    public List<BookDTO> getAllBooks() {
        return bookService.getAllBooks().stream().map(BookDTO::getBookDTO).collect(Collectors.toList());
    }

    @GetMapping("/getBook")
    public BookDataDTO editBookPage(
            @RequestParam("id") String id
    ) throws NotFoundException {
        Book book = bookService.getBook(id);
        List<Author> authors = authorService.getAllAuthors();
        List<Genre> genres = genreService.getAllGenres();
        return BookDataDTO.getBookDataDTO(book, authors, genres);
    }

    @PostMapping("/editBook")
    public String editBook(
            @RequestBody BookCreateDTO bookCreateDTO
    ) throws NotFoundException, ParseException {
        Book book = BookCreateDTO.getBook(bookCreateDTO);
        Set<Author> authors = authorService.getAuthorsById(bookCreateDTO.getAuthors());
        book.setAuthors(authors);
        Genre genre = genreService.getGenreById(bookCreateDTO.getGenre());
        book.setGenre(genre);
        bookService.updateBook(book);
        return "success";
    }

    @GetMapping("/getBookData")
    public BookDataDTO createBookPage() {
        List<Author> authors = authorService.getAllAuthors();
        List<Genre> genres = genreService.getAllGenres();
        return BookDataDTO.getBookDataDTO(null, authors, genres);
    }

    @PostMapping(value = "/createBook")
    public String createBook(@RequestBody BookCreateDTO bookCreateDTO) throws EntityExistsException, ParseException, NotFoundException {
        Book book = BookCreateDTO.getBook(bookCreateDTO);
        Set<Author> authors = authorService.getAuthorsById(bookCreateDTO.getAuthors());
        book.setAuthors(authors);
        Genre genre = genreService.getGenreById(bookCreateDTO.getGenre());
        book.setGenre(genre);
        bookService.createBook(book);
        return "success";
    }
}
