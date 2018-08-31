package com.springfreamwork.thymeleafajax.domain.services;


import com.springfreamwork.thymeleafajax.app.utility.EntityExistsException;
import com.springfreamwork.thymeleafajax.app.utility.NotFoundException;
import com.springfreamwork.thymeleafajax.domain.model.Author;

import java.util.List;
import java.util.Set;

public interface AuthorService {

    List<Author> getAllAuthors();

    long countAuthors();

    void createAuthor(Author author) throws EntityExistsException;

    Author getAuthorById(String authorId) throws NotFoundException;

    Set<Author> getAuthorsById(Set<String> authorsId) throws NotFoundException;

    void updateAuthor(Author author) throws NotFoundException;

    void deleteAuthor(String id);
}
