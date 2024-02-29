package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.model.CommentEntity;

public interface CommentService {

    CommentEntity getComment(Integer id);

    Comments getComments(Integer adId);

    Comment createComment(Integer adId, CreateOrUpdateComment createOrUpdateCommentDto, Authentication authentication);

    void deleteComment(Integer adId, Integer commentId, Authentication authentication);

    Comment updateComment(Integer adId, Integer commentId, CreateOrUpdateComment createOrUpdateCommentDto, Authentication authentication);
}
