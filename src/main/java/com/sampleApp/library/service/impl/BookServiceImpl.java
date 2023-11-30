package com.sampleApp.library.service.impl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.sampleApp.library.model.dto.BookDTO;
import com.sampleApp.library.model.dto.CommentDTO;
import com.sampleApp.library.model.entity.Book;
import com.sampleApp.library.model.mapper.BookMapper;
import com.sampleApp.library.model.mapper.CommentMapper;
import com.sampleApp.library.model.request.BookRequest;
import com.sampleApp.library.repository.BookRepository;
import com.sampleApp.library.repository.CommentRepository;
import com.sampleApp.library.service.BookService;

import jakarta.servlet.ServletContext;

@Service
public class BookServiceImpl implements BookService {
    
    

    private final BookRepository bookRepository;

    private final CommentRepository commentRepository;

    private final BookMapper bookMapper;

    private final CommentMapper commentMapper;

    private ServletContext servletContext;

    BookServiceImpl(BookRepository bookRepository,
            CommentRepository commentRepository, BookMapper bookMapper,
            CommentMapper commentMapper, ServletContext servletContext) {
        this.bookRepository = bookRepository;
        this.commentRepository = commentRepository;
        this.bookMapper = bookMapper;
        this.commentMapper = commentMapper;
        this.servletContext = servletContext;
    }

    @Override
    public BookDTO getBook(Long bookId) {
        var book = this.bookRepository.findById(bookId);
        if (book.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.bookMapper.toDTO(book.get(), null);
    }

    @Override
    public Page<BookDTO> getAllBook(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        List<BookDTO> bookDTOList = new ArrayList<>();
        var books = this.bookRepository.findAll(pageRequest);

        for (var book : books) {
            bookDTOList.add(this.bookMapper.toDTO(book, List.of()));
        }
        return new PageImpl<>(bookDTOList, books.getPageable(),
                books.getTotalElements());
    }

    @Override
    public BookDTO createBook(BookRequest bookReq) throws FileNotFoundException, IOException {
        String imgDir = "";
        String absolutePath = servletContext.getRealPath("resources/uploads");
        System.out.println(absolutePath);
        if (bookReq.getCoverImg().length() > 0) {
            String[] parts = bookReq.getCoverImg().split(",");
            String[] imageInfo = parts[0].split(";")[0].split("/");
            String imageFormat = imageInfo[1];
            System.out.println(bookReq.getCoverImg());
            String encodedImage = bookReq.getCoverImg().replaceAll("data:image/[a-zA-Z]+;base64,", "");
            byte[] imageByte = Base64.getDecoder().decode(encodedImage);
            
            imgDir = servletContext.getRealPath("/")
                    + "resources/images/" + bookReq.getTitle() + "."
                    + imageFormat;

           var fos = new FileOutputStream(imgDir);
           fos.write(imageByte);
           fos.close();
        }
        System.out.println(this.bookMapper.toEntity(bookReq, imgDir).toString());
        var book = this.bookRepository.save(this.bookMapper.toEntity(bookReq, imgDir));
        var bookDTO = this.bookMapper.toDTO(book, null);
        return bookDTO;
    }

    @Override
    public List<CommentDTO> getAllComment(Long bookId) {
        var comments = this.commentRepository.findAllCommentDetail(bookId);
        return this.commentMapper.toDTOList(comments);
    }

}
