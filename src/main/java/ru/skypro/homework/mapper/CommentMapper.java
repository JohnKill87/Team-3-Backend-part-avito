package ru.skypro.homework.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.model.CommentEntity;

@Component
public class CommentMapper {

//  Из Entity в DTO

    public Comment mapToComment(CommentEntity commentEntity) {
        Comment comments = new Comment();
        comments.setId(commentEntity.getId());
        comments.setText(commentEntity.getText());
        comments.setCreatedAT(commentEntity.getCreatedAT());
        comments.setAuthor(commentEntity.getAuthor());
        comments.setAd(commentEntity.getAd());
        return comments;
    }

    //    из DTO в Entity
    public CommentEntity mapToCommentEntity(Comment comments) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setId(comments.getId());
        commentEntity.setText(comments.getText());
        commentEntity.setCreatedAT(comments.getCreatedAT());
        commentEntity.setAuthor(comments.getAuthor());
        commentEntity.setAd(comments.getAd());
        return commentEntity;
    }

}