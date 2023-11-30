package com.sampleApp.library.model.projections;

import java.time.LocalDateTime;

public interface CommentProjection {

    String getCommentText();

    String getCommenterEmail();
    
    LocalDateTime getCreatedAt();
    
    LocalDateTime getUpdatedAt();

}
