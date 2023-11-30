package com.sampleApp.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sampleApp.library.model.entity.Comment;
import com.sampleApp.library.model.projections.CommentProjection;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	
	void deleteByBookId(Long id);
	
    @Query("""
            SELECT 
                c.email AS commenterEmail, 
                comment.commentText AS commentText,
                comment.createdAt AS createdAt,
                comment.updatedAt As updatedAt
            FROM Comment comment
            JOIN Commenter c ON comment.commenterId = c.id
            WHERE comment.bookId = :bookId
            ORDER BY
                comment.createdAt ASC
            """)
    List<CommentProjection> findAllCommentDetail(Long bookId);
}
