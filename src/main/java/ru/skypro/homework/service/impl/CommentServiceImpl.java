package ru.skypro.homework.service.impl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.exception.CommentNotFoundException;
import ru.skypro.homework.exception.ForbiddenException;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.model.AdEntity;
import ru.skypro.homework.model.CommentEntity;
import ru.skypro.homework.model.UserEntity;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final AdService adService;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final UserService userService;

    @Override
    public CommentEntity getComment(Integer commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException(commentId));
    }

    @Override
    public Comments getComments(Integer adId) {
        List<CommentEntity> comments = commentRepository.findAllByAdPk(adId);
        return commentMapper.toCommentsDto(comments);
    }

    @Override
    public Comment createComment(Integer adId,
                                    CreateOrUpdateComment createOrUpdateCommentDto,
                                    Authentication authentication) {
        UserEntity userEntity = userService.getUserByEmail(authentication.getName());
        AdEntity adEntity = adService.getAd(adId);
        CommentEntity commentEntity = commentMapper.toEntity(createOrUpdateCommentDto);
        commentEntity.setAd(adEntity);
        commentEntity.setUser(userEntity);
        return commentMapper.toCommentDto(commentRepository.save(commentEntity));
    }

    @Override
    public void deleteComment(Integer adId, Integer commentId, Authentication authentication) {
        if (isAdminOrOwner(commentId, authentication)) {
            CommentEntity commentEntity = getComment(commentId);
            commentRepository.delete(commentEntity);
            return;
        }
        throw new ForbiddenException("No permission to delete this comment");
    }

    @Override
    public Comment updateComment(Integer adId,
                                 Integer commentId,
                                 CreateOrUpdateComment createOrUpdateCommentDto,
                                 Authentication authentication) {
        if (isAdminOrOwner(commentId, authentication)) {
            CommentEntity commentEntity = getComment(commentId);
            commentEntity.setText(createOrUpdateCommentDto.getText());
            return commentMapper.toCommentDto(commentRepository.save(commentEntity));
        }
        throw new ForbiddenException("No permission to edit this comment");
    }

    private boolean isAdminOrOwner(Integer commentId, Authentication authentication) {
        CommentEntity commentEntity = getComment(commentId);
        UserEntity userEntity = userService.getUserByEmail(authentication.getName());
        return userEntity.equals(commentEntity.getUser()) || userEntity.getRole().equals(Role.ADMIN.name());
    }
}