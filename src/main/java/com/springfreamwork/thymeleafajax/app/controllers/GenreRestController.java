package com.springfreamwork.thymeleafajax.app.controllers;

import com.springfreamwork.thymeleafajax.app.utility.EntityExistsException;
import com.springfreamwork.thymeleafajax.app.utility.NotFoundException;
import com.springfreamwork.thymeleafajax.domain.dto.GenreDTO;
import com.springfreamwork.thymeleafajax.domain.model.Genre;
import com.springfreamwork.thymeleafajax.domain.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GenreRestController {

    private final GenreService genreService;

    @Autowired
    public GenreRestController(
            GenreService genreService
    ) {
        this.genreService = genreService;
    }

    @DeleteMapping("/deleteGenre")
    public String deleteGenre(@RequestParam("id") String id) {
        genreService.deleteGenre(id);
        return "success";
    }

    @GetMapping("/getGenres")
    public List<Genre> getAllGenres() {
        return genreService.getAllGenres();
    }

    @PostMapping("/editGenre")
    public String editGenre(
            @RequestBody GenreDTO genreDTO) throws NotFoundException {
        Genre genre = genreService.getGenreById(genreDTO.getId());
        genre.setName(genreDTO.getName());
        genreService.updateGenre(genre);
        return "success";
    }

    @PostMapping("/createGenre")
    public String createGenre(
            @RequestBody GenreDTO genreDTO) throws EntityExistsException {
        Genre genre = genreDTO.toGenre();
        genreService.createGenre(genre);
        return "success";
    }

}
