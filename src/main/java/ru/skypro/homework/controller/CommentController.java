package ru.skypro.homework.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.service.impl.AdServiceImpl;

@RestController
@RequestMapping("/ads")
public class CommentController {
    private final UserRepository userRepository;
    private final CommentService commentService;
    private final AdServiceImpl adService;

    public CommentController(UserRepository userRepository, CommentService commentService, AdServiceImpl adService) {
        this.userRepository = userRepository;
        this.commentService = commentService;
        this.adService = adService;
    }

    @GetMapping("{id}/comments")
    public ResponseEntity<Comments> getComment(@PathVariable("id") Integer id, Authentication authentication) {
        if (authentication.getName() != null) {
            return ResponseEntity.ok(commentService.getComments(id));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("{id}/comments")
    public ResponseEntity<Comment> addComment(@PathVariable("id") Integer id, @RequestBody CreateOrUpdateComment createOrUpdateComment, Authentication authentication) {
        return ResponseEntity.ok(commentService.addComment(id, createOrUpdateComment, authentication.getName()));
    }

    @DeleteMapping("{adId}/comments/{commentId}")
    @PreAuthorize(value = "hasRole('ADMIN') or @adServiceImpl.isAuthorAd(authentication.getName(), #adId)")
    public ResponseEntity<?> deleteComment(@PathVariable("adId") Integer adId, @PathVariable("commentId") Integer commentId, Authentication authentication) {
        if (authentication.getName() != null) {
            String result = commentService.deleteComment(commentId, authentication.getName());
            if (result.equals("forbidden")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            } else if (result.equals("not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            } else {
                return ResponseEntity.status(HttpStatus.OK).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PatchMapping("{adId}/comments/{commentId}")
    @PreAuthorize(value = "hasRole('ADMIN') or @adServiceImpl.isAuthorAd(authentication.getName(), #adId)")
    public ResponseEntity<Comment> updateComment(@PathVariable("adId") Integer adId, @PathVariable("commentId") Integer commentId, @RequestBody CreateOrUpdateComment createOrUpdateComment, Authentication authentication) {
        if (authentication.getName() != null) {
            Comment comment = commentService.updateComment(commentId, createOrUpdateComment, authentication.getName());
            return ResponseEntity.ok(comment);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}

