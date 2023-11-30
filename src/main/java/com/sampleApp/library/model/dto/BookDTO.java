package com.sampleApp.library.model.dto;

import java.util.List;


public record BookDTO(
        Long id,
        String title,
        String summary,
        String coverImg,
        String generes,
        String author,
        String tag,
        Integer rating,
        String pdfLink,
        List<Long> relatedBookId
        ) {}
