package com.springfreamwork.thymeleafajax.app.controllers;

import com.springfreamwork.thymeleafajax.app.utility.EntityExistsException;
import com.springfreamwork.thymeleafajax.app.utility.NotFoundException;
import com.springfreamwork.thymeleafajax.domain.dto.AuthorDTO;
import com.springfreamwork.thymeleafajax.domain.model.Author;
import com.springfreamwork.thymeleafajax.domain.model.Country;
import com.springfreamwork.thymeleafajax.domain.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class AuthorRestController {

    private final AuthorService authorService;

    @Autowired
    public AuthorRestController(
            AuthorService authorService
    ) {
        this.authorService = authorService;
    }

    @GetMapping("/getAuthors")
    public List<Author> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @DeleteMapping("/deleteAuthor")
    public String deleteGenre(@RequestParam("id") String id) {
        authorService.deleteAuthor(id);
        return "success";
    }

    @PostMapping("/editAuthor")
    public String editAuthor(
            @RequestBody AuthorDTO authorDTO
            ) throws NotFoundException {
        Author author = authorService.getAuthorById(authorDTO.getId());
        author.setName(authorDTO.getName());
        author.setSurname(authorDTO.getSurname());
        authorService.updateAuthor(author);
        return "success";
    }


    @GetMapping("/getCountries")
    public List<Country> getAllCountries() {
        return Arrays.asList(Country.values());
    }

    @PostMapping("/createAuthor")
    public String createAuthor(@RequestBody AuthorDTO authorDTO) throws EntityExistsException {
        Author author = authorDTO.toAuthor();
        authorService.createAuthor(author);
        return "success";
    }
}
