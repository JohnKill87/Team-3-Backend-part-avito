package ru.skypro.homework.service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CreateCommentDto;
import ru.skypro.homework.dto.FullCommentDto;
import ru.skypro.homework.exception.NonExistentAdException;
import ru.skypro.homework.exception.NonExistentCommentException;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.CommentRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository repository;
    private final UserService userService;
    private final AdService adService;
    private final CommentMapper commentMapper;

    /**
     * Return all comments of target ad
     * @param adId id of target ad
     * @return {@link List} of {@link Comment}
     */
    public List<Comment> findAllByAdId(int adId) {
        return repository.findAllByAdPk(adId);
    }


    public Optional<Comment> findById(int commentId) {
        return repository.findById(commentId);
    }

    /**
     * Create new comment
     * @param username author's username (email)
     * @param createCommentDto object with new comment's data
     * @param adId id of target ad
     * @return created {@link Comment}
     */
    public Comment createComment(String username, CreateCommentDto createCommentDto, int adId) {
        User author = userService.findUserByEmail(username);
        Ad targetAd = adService.findById(adId).orElseThrow(NonExistentAdException::new);
        return save(new Comment(
                targetAd,
                author,
                System.currentTimeMillis(),
                createCommentDto.getText()));
    }

    /**
     * Edit comment
     * @param commentId id of target comment
     * @param fullCommentDto object with new comment's data
     * @return edited comment in {@link FullCommentDto} instance
     */
    public FullCommentDto editComment(int commentId, FullCommentDto fullCommentDto) {
        Comment targetComment = findById(commentId).orElseThrow(NonExistentCommentException::new);
        targetComment.setText(fullCommentDto.getText());
        targetComment.setCreatingTime(System.currentTimeMillis());
        return commentMapper.mapCommentToFullCommentDto(save(targetComment));
    }

    /**
     * Save comment
     * @param comment target {@link Comment}
     * @return saved {@link Comment}
     */
    public Comment save(Comment comment) {
        return repository.save(comment);
    }

    /**
     * Delete target comment
     * @param id target comment's id
     */
    public void deleteById(int id) {
        repository.deleteById(id);
    }
}