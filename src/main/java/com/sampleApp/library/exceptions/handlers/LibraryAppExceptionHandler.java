package com.sampleApp.library.exceptions.handlers;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sampleApp.library.exceptions.InvalidBase64ImgException;
import com.sampleApp.library.exceptions.UploadPdfFileException;
import com.sampleApp.library.model.dto.ErrorResponseDTO;

@RestControllerAdvice
public class LibraryAppExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        ErrorResponseDTO errorResponse = new ErrorResponseDTO("Validation error",
            ex.getMessage(), errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponseDTO> handleNoSuchElementException(NoSuchElementException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
            "Not Found", ex.getMessage(), Map.of()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidBase64ImgException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidBase64ImgException(
        InvalidBase64ImgException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO("Validation error",
            ex.getMessage(), ex.getDetails());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UploadPdfFileException.class)
    public ResponseEntity<ErrorResponseDTO> handleUploadPdfFileException(
        UploadPdfFileException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO("Validation error",
            ex.getMessage(), ex.getDetails());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
