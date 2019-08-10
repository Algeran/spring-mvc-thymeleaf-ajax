package com.springfreamwork.thymeleafajax.domain.services;


import com.springfreamwork.thymeleafajax.app.utility.EntityExistsException;
import com.springfreamwork.thymeleafajax.app.utility.NotFoundException;
import com.springfreamwork.thymeleafajax.domain.model.Book;

import java.util.List;
import java.util.Set;

public interface BookService {
    List<Book> getAllBooks();

    long countBooks();

    void deleteBook(String id);

    Book getBook(String id) throws NotFoundException;

    void updateBook(Book book) throws NotFoundException;

    void createBook(Book book) throws EntityExistsException;

    Set<Book> getBooksById(Set<String> ids);
}
