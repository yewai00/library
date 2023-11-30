package com.sampleApp.library.model.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.sampleApp.library.model.dto.CommentDTO;
import com.sampleApp.library.model.projections.CommentProjection;

@Component
public class CommentMapper {
    public CommentDTO toDTO(CommentProjection commentProjection) {
        return new CommentDTO(
                commentProjection.getCommenterEmail(),
                commentProjection.getCommentText(),
                commentProjection.getCreatedAt(),
                commentProjection.getUpdatedAt()
                );
    }

    public List<CommentDTO> toDTOList(List<CommentProjection> commentProjectionList) {
        List<CommentDTO> commentDTO = new ArrayList<>();
        for(CommentProjection commentPj: commentProjectionList) {
            commentDTO.add(this.toDTO(commentPj));
        }
        return commentDTO;
    }
}
