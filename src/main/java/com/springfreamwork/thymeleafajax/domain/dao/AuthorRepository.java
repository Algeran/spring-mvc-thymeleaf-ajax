package com.springfreamwork.thymeleafajax.domain.dao;

import com.springfreamwork.thymeleafajax.domain.model.Author;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AuthorRepository extends MongoRepository<Author, String> {

    Optional<Author> findByNameAndSurname(String name, String surname);

    void deleteByNameAndSurname(String name, String surname);
}
