package com.sampleApp.library.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sampleApp.library.model.dto.BookDTO;
import com.sampleApp.library.model.dto.CommentDTO;
import com.sampleApp.library.model.request.BookRequest;
import com.sampleApp.library.model.request.CommentRequest;
import com.sampleApp.library.service.BookService;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;

    BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<Page<BookDTO>> getAllBook(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        var books = this.bookService.getAllBook(page, size);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBook(@PathVariable Long id) {
        var book = this.bookService.getBook(id);
        return ResponseEntity.ok(book);
    }

    @PostMapping
    public ResponseEntity<BookDTO> createBook(
            @RequestBody BookRequest bookRequest)
            throws FileNotFoundException, IOException {
        var book = this.bookService.createBook(bookRequest);
        return ResponseEntity.created(null).body(book);
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentDTO>> getAllComment(
            @PathVariable Long id) {
        var comments = this.bookService.getAllComment(id);
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<?> postComment(@PathVariable Long id,
            CommentRequest commentRequest) {

        return ResponseEntity.created(null).body(null);
    }

}
