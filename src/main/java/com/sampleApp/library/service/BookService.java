package com.sampleApp.library.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.sampleApp.library.model.dto.BookDTO;
import com.sampleApp.library.model.dto.CommentDTO;
import com.sampleApp.library.model.request.BookRequest;
import com.sampleApp.library.model.request.CommentRequest;

public interface BookService {
    public Page<BookDTO> getAllBook(int page, int size);

    public BookDTO getBook(Long bookId);

    public BookDTO createBook(BookRequest bookReq)
            throws FileNotFoundException, IOException;
    
    public BookDTO updateBook(Long bookId, BookRequest bookReq) 
            throws FileNotFoundException, IOException;
    
    public void deleteBook(Long bookId);
    
    public void storePdf(Long id, MultipartFile file) throws IOException;

    public List<CommentDTO> getAllComment(Long BookId);

    public void createComment(Long id, CommentRequest commentReq);
}
