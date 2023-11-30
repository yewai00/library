package com.sampleApp.library.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;

import com.sampleApp.library.model.dto.BookDTO;
import com.sampleApp.library.model.dto.CommentDTO;
import com.sampleApp.library.model.request.BookRequest;

public interface BookService {
    public Page<BookDTO> getAllBook(int page, int size);

    public BookDTO getBook(Long bookId);

    public BookDTO createBook(BookRequest bookReq)
            throws FileNotFoundException, IOException;

    public List<CommentDTO> getAllComment(Long BookId);
}
