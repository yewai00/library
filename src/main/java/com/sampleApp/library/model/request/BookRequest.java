package com.sampleApp.library.model.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
    private String genres;

    @NotEmpty(message = "Author is required.")
    @Size(max = 255)
    private String author;

    @Size(max = 255)
    private String tag;

    @Min(value = 0, message = "Rating Value must be between 0 to 5")
    @Max(value = 5, message = "Rating Value must be between 0 to 5")
    private int rating;
}
