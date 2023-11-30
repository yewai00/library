package com.sampleApp.library.model.dto;

import java.time.LocalDateTime;

public record CommentDTO(
        String commenterEmail,
        String comment,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
        ) {}
