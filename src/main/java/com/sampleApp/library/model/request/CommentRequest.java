package com.sampleApp.library.model.request;

import lombok.Data;

@Data
public class CommentRequest {
    private String email;
    private String comment;
}
