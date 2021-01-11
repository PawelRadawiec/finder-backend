package com.info.finder.controller;

import com.info.finder.model.Article;
import com.info.finder.model.Comment;
import com.info.finder.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController()
@CrossOrigin("*")
@RequestMapping(value = "/comment/")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping(value = "{articleId}")
    public ResponseEntity<Article> add(
            @PathVariable String articleId,
            @Valid  @RequestBody Comment comment
    ) {
        return new ResponseEntity<>(commentService.add(articleId, comment), HttpStatus.OK);
    }

}
