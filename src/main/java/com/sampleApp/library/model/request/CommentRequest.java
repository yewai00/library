package com.sampleApp.library.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentRequest {
    @Email
    @NotEmpty(message = "Email is required.")
    @Size(max = 255)
    private String email;

    @NotEmpty(message = "Comment is required.")
    @Size(max = 255)
    private String comment;
}
