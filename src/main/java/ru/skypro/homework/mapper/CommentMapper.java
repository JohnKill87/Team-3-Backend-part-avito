package ru.skypro.homework.mapper;


import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.model.CommentEntity;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentMapper {

    public CommentEntity toEntity(CreateOrUpdateComment createOrUpdateCommentDto) {
        CommentEntity commentEntity = new CommentEntity();

        commentEntity.setText(createOrUpdateCommentDto.getText());
        commentEntity.setCreatedAt(new Timestamp(System.currentTimeMillis()).getTime());

        return commentEntity;
    }

    public Comment toCommentDto(CommentEntity commentEntity) {
        Comment comment = new Comment();

        comment.setPk(commentEntity.getPk());
        comment.setAuthor(commentEntity.getUser().getId());
        comment.setAuthorImage(commentEntity.getUser().getAvatar());
        comment.setAuthorFirstName(commentEntity.getUser().getFirstName());
        comment.setCreatedAt(commentEntity.getCreatedAt());
        comment.setText(commentEntity.getText());

        return comment;
    }

    public Comments toCommentsDto(List<CommentEntity> comments) {
        Comments commentsDto = new Comments();
        List<Comment> commentDtoList = comments.stream()
                .map(this::toCommentDto)
                .collect(Collectors.toList());

        commentsDto.setCount(commentDtoList.size());
        commentsDto.setResult(commentDtoList);

        return commentsDto;
    }
}