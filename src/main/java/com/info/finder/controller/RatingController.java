package com.info.finder.controller;

import com.info.finder.model.Article;
import com.info.finder.model.Rating;
import com.info.finder.service.RatingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/rating/")
@CrossOrigin("*")
public class RatingController {

    private RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PutMapping(value = "/evaluate/{articleId}/{commentId}")
    public ResponseEntity<Article> evaluate(
            @RequestBody Rating rating,
            @PathVariable String articleId,
            @PathVariable Long commentId
    ) {
        return new ResponseEntity<>(ratingService.evaluate(articleId, commentId, rating), HttpStatus.OK);
    }

}
