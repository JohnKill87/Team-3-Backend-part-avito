package ru.skypro.homework.mapper;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.model.AdEntity;
import ru.skypro.homework.model.CommentEntity;
import ru.skypro.homework.model.UserEntity;

@Service
public class CommentMapper {

//  Из Entity в DTO

    public Comment mapToComment(CommentEntity commentEntity) {
        Comment comment = new Comment();
        comment.setId(commentEntity.getId());
        comment.setText(commentEntity.getText());
        comment.setCreatedAT(commentEntity.getCreatedAT());
        comment.setAuthor(commentEntity.getAuthor());
        comment.setAd(commentEntity.getAd());
        return comment;
    }

    //    из DTO в Entity
    public CommentEntity mapToCommentEntity(Comment comment) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setId(comment.getId());
        commentEntity.setText(comment.getText());
        commentEntity.setCreatedAT(comment.getCreatedAT());
        commentEntity.setAuthor(comment.getAuthor());
        commentEntity.setAd(comment.getAd());
        return commentEntity;
    }
}