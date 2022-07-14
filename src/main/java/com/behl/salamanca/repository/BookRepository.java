package com.behl.salamanca.repository;

import java.util.List;
import java.util.Optional;

import com.behl.salamanca.entity.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


/**
 * @author shrisowdhaman
 * Dec 14, 2017
 */

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    List<Book> findByTitle(String title);

    Optional<Book> findById(long id);
}
