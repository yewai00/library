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
import org.springframework.web.multipart.MultipartFile;

import com.sampleApp.library.common.Constants;
import com.sampleApp.library.model.dto.BookDTO;
import com.sampleApp.library.model.dto.CommentDTO;
import com.sampleApp.library.model.entity.Book;
import com.sampleApp.library.model.entity.Comment;
import com.sampleApp.library.model.entity.Commenter;
import com.sampleApp.library.model.mapper.BookMapper;
import com.sampleApp.library.model.mapper.CommentMapper;
import com.sampleApp.library.model.request.BookRequest;
import com.sampleApp.library.model.request.CommentRequest;
import com.sampleApp.library.repository.BookRepository;
import com.sampleApp.library.repository.CommentRepository;
import com.sampleApp.library.repository.CommenterRepository;
import com.sampleApp.library.service.BookService;

@Service
public class BookServiceImpl implements BookService {
    
    private final BookRepository bookRepository;

    private final CommentRepository commentRepository;
    
    private final CommenterRepository commenterRepository;

    private final BookMapper bookMapper;

    private final CommentMapper commentMapper;


    BookServiceImpl(BookRepository bookRepository,
            CommentRepository commentRepository,
            CommenterRepository commenterRepository,
            BookMapper bookMapper,
            CommentMapper commentMapper
    		) {
        this.bookRepository = bookRepository;
        this.commentRepository = commentRepository;
        this.commenterRepository = commenterRepository;
        this.bookMapper = bookMapper;
        this.commentMapper = commentMapper;
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
            var relatedBookIds = this.bookRepository.findRelatedBook(book.getId());
            var first7relatedBookIds = getFirstNItems(relatedBookIds, 7);
            bookDTOList.add(this.bookMapper.toDTO(book, first7relatedBookIds));
        }
        return new PageImpl<>(bookDTOList, books.getPageable(),
                books.getTotalElements());
    }
    
    @Override
    public BookDTO createBook(BookRequest bookReq) throws FileNotFoundException, IOException {
        String imgDirToStore = "";
        String imgDirToDb = "";
        if (bookReq.getCoverImg().length() > 0) {
            String[] parts = bookReq.getCoverImg().split(",");
            String[] imageInfo = parts[0].split(";")[0].split("/");
            String imageFormat = imageInfo[1];
            
            String encodedImage = bookReq.getCoverImg().replaceAll("data:image/[a-zA-Z]+;base64,", "");
            byte[] imageByte = Base64.getDecoder().decode(encodedImage);
            
            imgDirToStore = Constants.imagePath + bookReq.getTitle().replace(" ", "_") + "."
                    + imageFormat;
            imgDirToDb = "/resources/images/" + bookReq.getTitle().replace(" ", "_") + "."
                    + imageFormat;

           var fos = new FileOutputStream(imgDirToStore);
           fos.write(imageByte);
           fos.close();
        }
        var book = this.bookRepository.save(this.bookMapper.toEntity(bookReq, imgDirToDb));
        var bookDTO = this.bookMapper.toDTO(book, null);
        return bookDTO;
    }
    
    @Override
	public BookDTO updateBook(Long bookId, BookRequest bookReq) throws FileNotFoundException, IOException {
		var bookOpt = this.bookRepository.findById(bookId);
		if(!bookOpt.isPresent()) {
			throw new NoSuchElementException();
		}
		String imgDirToStore = "";
        String imgDirToDb = "";
        if (bookReq.getCoverImg().length() > 0) {
            String[] parts = bookReq.getCoverImg().split(",");
            String[] imageInfo = parts[0].split(";")[0].split("/");
            String imageFormat = imageInfo[1];
            
            String encodedImage = bookReq.getCoverImg().replaceAll("data:image/[a-zA-Z]+;base64,", "");
            byte[] imageByte = Base64.getDecoder().decode(encodedImage);
            
            imgDirToStore = Constants.imagePath + bookReq.getTitle().replace(" ", "_") + "."
                    + imageFormat;
            imgDirToDb = "/resources/images/" + bookReq.getTitle().replace(" ", "_") + "."
                    + imageFormat;

           var fos = new FileOutputStream(imgDirToStore);
           fos.write(imageByte);
           fos.close();
        }
        var bookEntity = this.bookMapper.toEntity(bookReq, imgDirToDb);
        bookEntity.setId(bookId);
        bookEntity.setPdfLink(bookOpt.get().getPdfLink());
        var book = this.bookRepository.save(bookEntity);
        var bookDTO = this.bookMapper.toDTO(book, null);
        return bookDTO;
	}
    
	@Override
	public void deleteBook(Long bookId) {
		this.bookRepository.deleteById(bookId);
		this.commentRepository.deleteByBookId(bookId);
	}

    @Override
    public List<CommentDTO> getAllComment(Long bookId) {
        var comments = this.commentRepository.findAllCommentDetail(bookId);
        return this.commentMapper.toDTOList(comments);
    }

	@Override
	public void storePdf(Long id, MultipartFile file) throws IOException {
		 if (file.isEmpty()) {
			 throw new RuntimeException(); //TODO
	     }

	     if (!file.getContentType().equalsIgnoreCase("application/pdf")) {
	    	 throw new RuntimeException(); //TODO
	     }
	        
	    
		var bookOpt = this.bookRepository.findById(id);
		if(!bookOpt.isPresent()) {
			throw new NoSuchElementException(); //TODO: should throw with message
		}
		
		var book = bookOpt.get();
		String fileName = file.getOriginalFilename();
		byte[] bytes = file.getBytes();
		String imgDirToStore = Constants.pdfPath + fileName ;
		var fos = new FileOutputStream(imgDirToStore);
        fos.write(bytes);
        fos.close();
        String imgDirToDb = "/resources/pdfs/" + fileName;
        book.setPdfLink(imgDirToDb);
        this.bookRepository.save(book);
		
	}

	@Override
	public void createComment(Long id, CommentRequest commentReq) {
		Long commenterId = null;
		Optional<Book> book = this.bookRepository.findById(id);
		if(!book.isPresent()) {
			throw new NoSuchElementException();
		}
		
		Optional<Commenter> commenter = this.commenterRepository.findByEmail(commentReq.getEmail());
		if(!commenter.isPresent()) {
			var c = new Commenter();
			c.setEmail(commentReq.getEmail());
			var newCommenter = this.commenterRepository.save(c);
			commenterId = newCommenter.getId();
		} else {
			commenterId = commenter.get().getId();			
		}
		var comment = new Comment();
		comment.setBookId(id);
		comment.setCommenterId(commenterId);
		comment.setCommentText(commentReq.getComment());
		this.commentRepository.save(comment);
	}

    private static <T> List<T> getFirstNItems(List<T> list, int n) {
        int endIndex = Math.min(n, list.size());
        return list.subList(0, endIndex);
    }

}
