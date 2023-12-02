package com.sampleApp.library.model.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.sampleApp.library.enums.Ratings;
import com.sampleApp.library.model.dto.BookDTO;
import com.sampleApp.library.model.entity.Book;
import com.sampleApp.library.model.request.BookRequest;

@Component
public class BookMapper {
    public BookDTO toDTO(Book book, List<Long> relatedBookId) {
        return new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getSummary(),
                book.getCoverImg(),
                book.getGenres(),
                book.getAuthor(),
                book.getTag(),
                book.getRating().ordinal(),
                book.getPdfLink(),
                relatedBookId
                );
    }
    
    public Book toEntity(BookRequest bookReq, String imgDir) {
        Book book = new Book();
        book.setTitle(bookReq.getTitle());
        book.setSummary(bookReq.getSummary());
        book.setCoverImg(imgDir);
        book.setGenres(bookReq.getGenres());
        book.setAuthor(bookReq.getAuthor());
        book.setTag(bookReq.getTag());
        book.setRating(Ratings.values()[bookReq.getRating()]);
        return book;
    }

}
