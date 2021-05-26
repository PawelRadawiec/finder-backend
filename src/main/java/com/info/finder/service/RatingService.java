package com.info.finder.service;

import com.info.finder.model.Article;
import com.info.finder.model.Comment;
import com.info.finder.model.Rating;
import com.info.finder.repository.RatingRepository;
import org.springframework.stereotype.Service;

@Service
public class RatingService {

    private ArticleService articleService;
    private CommentService commentService;
    private RatingRepository ratingRepository;

    public RatingService(ArticleService articleService, CommentService commentService, RatingRepository ratingRepository) {
        this.articleService = articleService;
        this.commentService = commentService;
        this.ratingRepository = ratingRepository;
    }

    public Article evaluate(String articleId, Long commentId, Rating rating) {
        Article article = articleService.getById(articleId);
        Comment comment = commentService.getCommentById(commentId, article);
        if (comment != null && rating != null) {
            rating.setAuthor(SystemUserHelper.username());
            if (commentService.containsRating(comment, rating)) {
                article = ratingRepository.updateRatting(articleId, commentId, rating);
            } else {
                comment.getRatings().add(rating);
                article = articleService.create(article);
            }
        }
        return article;
    }


}
