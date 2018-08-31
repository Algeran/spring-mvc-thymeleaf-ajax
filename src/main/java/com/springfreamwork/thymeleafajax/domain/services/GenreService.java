package com.springfreamwork.thymeleafajax.domain.services;


import com.springfreamwork.thymeleafajax.app.utility.EntityExistsException;
import com.springfreamwork.thymeleafajax.app.utility.NotFoundException;
import com.springfreamwork.thymeleafajax.domain.model.Genre;

import java.util.List;

public interface GenreService {

    Genre createGenre(Genre genre) throws EntityExistsException;

    List<Genre> getAllGenres();

    long countGenres();

    void deleteGenre(String id);

    void updateGenre(Genre genre) throws NotFoundException;

    Genre getGenreById(String genreId) throws NotFoundException;
}
