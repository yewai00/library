package com.sampleApp.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sampleApp.library.model.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("""
            SELECT b.id FROM Book b
            WHERE b.id <> :bookId 
            ORDER BY (CASE WHEN b.author = (SELECT author FROM Book WHERE id = :bookId) THEN 1 ELSE 0 END, 
                     CASE WHEN b.genres = (SELECT genres FROM Book WHERE id = :bookId) THEN 1 ELSE 0 END,
                     CASE WHEN b.tag = (SELECT tag FROM Book WHERE id = :bookId) THEN 1 ELSE 0 END,
                     CASE WHEN b.rating = (SELECT rating FROM Book WHERE id = :bookId) THEN 1 ELSE 0 END) DESC""")
    public List<Long> findRelatedBook(Long bookId);
}
