package com.sampleApp.library.model.request;

import com.sampleApp.library.enums.Ratings;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BookRequest {
    @NotEmpty(message = "Title is required field.")
    @Size(max = 255)
    private String title;

    @Size(max = 255)
    private String summary;

    private String coverImg;

    @NotEmpty(message = "Genres is required.")
    @Size(max = 255)
    private String generes;

    @NotEmpty(message = "Author is required.")
    @Size(max = 255)
    private String author;

    @Size(max = 255)
    private String tag;

    @NotEmpty(message = "Rating is required.")
    private Ratings rating;
}
