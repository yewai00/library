package com.sampleApp.library.model.request;

import com.sampleApp.library.enums.Ratings;

import lombok.Data;

@Data
public class BookRequest {
    private String title;
    private String summary;
    private String coverImg;
    private String generes;
    private String author;
    private String tag;
    private Ratings rating;
}
