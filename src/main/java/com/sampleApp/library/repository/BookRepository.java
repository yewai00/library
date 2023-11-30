package com.sampleApp.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sampleApp.library.model.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
