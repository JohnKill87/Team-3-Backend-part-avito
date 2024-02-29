package ru.skypro.homework.mapper;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.FullCommentDto;
import ru.skypro.homework.dto.ResponseWrapperComment;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    /**
     * Map {@link List} of {@link Comment} to {@link ResponseWrapperComment}
     *
     * @param comments target {@link List} of {@link Comment}
     * @return created {@link ResponseWrapperComment}
     */
    public ResponseWrapperComment mapCommentListToWrapper(List<Comment> comments) {
        List<FullCommentDto> collect = comments.stream().map(this::mapCommentToFullCommentDto)
                .collect(Collectors.toList());
        return new ResponseWrapperComment(collect.size(), collect);
    }

    /**
     * Map {@link Comment} to {@link FullCommentDto}
     *
     * @param comment target {@link Comment}
     * @return created {@link FullCommentDto}
     */
    public FullCommentDto mapCommentToFullCommentDto(Comment comment) {
        User author = comment.getAuthor();
        return new FullCommentDto(
                author.getUserId(),
                author.getImage(),
                author.getFirstName(),
                comment.getCreatingTime(),
                comment.getId(),
                comment.getText()
        );
    }
}