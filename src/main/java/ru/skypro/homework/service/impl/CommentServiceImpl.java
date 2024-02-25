package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.model.AdEntity;
import ru.skypro.homework.model.CommentEntity;
import ru.skypro.homework.model.UserEntity;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.CommentService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final AdRepository adRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;
    private final UserServiceImpl userService;

    public CommentServiceImpl(CommentRepository commentRepository, AdRepository adRepository, UserRepository userRepository, CommentMapper commentMapper, UserServiceImpl userService) {
        this.commentRepository = commentRepository;
        this.adRepository = adRepository;
        this.userRepository = userRepository;
        this.commentMapper = commentMapper;
        this.userService = userService;
    }

    @Override
    public Comments getComments(Integer id) {
        List<Comment> comments = commentRepository.findByAdId(id).stream()
                .map(comment -> commentMapper.mapToComment(comment))
                .collect(Collectors.toList());
        return new Comments(comments.size(), comments);
    }

    @Override
    public Comment addComment(Integer id, CreateOrUpdateComment createOrUpdateComment, String username) {
        UserEntity author = userRepository.findUserByEmail(username);
        AdEntity ad = adRepository.findById(id).orElse(null);

        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setAuthor(author);
        commentEntity.setAd(ad);
        commentEntity.setText(createOrUpdateComment.getText());
        commentEntity.setCreatedAT(System.currentTimeMillis());

        commentRepository.save(commentEntity);

        author.getComments().add(commentEntity);
        userRepository.save(author);

        Comment commentDTO = new Comment();
        commentDTO.setAuthor(author);
        commentDTO.setId(commentEntity.getId());
        commentDTO.setText(createOrUpdateComment.getText());
        commentDTO.setCreatedAT(commentEntity.getCreatedAT());
        commentDTO.setAd(commentEntity.getAd());

        return commentDTO;
    }

    @Override
    public String deleteComment(Integer commentId, String username) {
        Optional<CommentEntity> comment = commentRepository.findById(commentId);
        if (comment.isPresent()) {
            UserEntity author = userRepository.findUserByEmail(username);
            if (author.getRole().equals(Role.ADMIN)) {
                commentRepository.delete(comment.get());
                return "комментарий удален";
            } else if (author.getRole().equals(Role.USER)) {
                if (comment.get().getAuthor().getEmail().equals(author.getEmail())) {
                    commentRepository.delete(comment.get());
                    return "комментарий удален";
                } else {
                    return "forbidden";
                }
            }
        }
        return "not found";
    }

    @Override
    public Comment updateComment(Integer commentId, CreateOrUpdateComment createOrUpdateComment, String username) {
        Optional<CommentEntity> commentOptional = commentRepository.findById(commentId);
        if (commentOptional.isPresent()) {
            CommentEntity comment = commentOptional.get();
            UserEntity author = userRepository.findUserByEmail(username);
            if (author.getComments().contains(comment)) {
                comment.setText(createOrUpdateComment.getText());
                commentRepository.save(comment);
                return commentMapper.mapToComment(comment);
            } else {
                return commentMapper.mapToComment(comment);
            }
        }
        return null;
    }
}