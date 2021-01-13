package com.info.finder.service;

import com.info.finder.model.Article;
import com.info.finder.model.Comment;
import com.info.finder.model.Rating;
import com.info.finder.repository.sequence.NextSequenceService;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class RatingService {

    private ArticleService articleService;
    private NextSequenceService nextSequenceService;
    private MongoTemplate mongoTemplate;

    public RatingService(ArticleService articleService, NextSequenceService nextSequenceService, MongoTemplate mongoTemplate) {
        this.articleService = articleService;
        this.nextSequenceService = nextSequenceService;
        this.mongoTemplate = mongoTemplate;
    }

    public Article evaluate(String articleId, Long commentId, Rating rating) {
        Article article = articleService.getById(articleId);
        Comment comment = article.getComments().stream()
                .filter(c -> c.getId() == commentId)
                .findAny()
                .orElse(null);
        if (comment != null) {
            boolean containsRating = comment.getRatings().stream()
                    .filter(r -> r.getAuthor().equals(rating.getAuthor()))
                    .findAny()
                    .orElse(null) != null;
            if (containsRating) {
                article = updateRatting(articleId, commentId, rating);
            } else {
                comment.getRatings().add(rating);
                article = articleService.create(article);
            }
        }
        return article;
    }

    public Article updateRatting(String articleId, Long commentId, Rating rating) {
        Query query = new Query(new Criteria().andOperator(
                Criteria.where("_id").is(articleId)
        ));
        Update update = new Update()
                .set("comments.$[comment].ratings.$[rating]", rating)
                .filterArray(Criteria.where("comment._id").is(commentId))
                .filterArray(Criteria.where("rating.author").is(rating.getAuthor()));
        FindAndModifyOptions options = FindAndModifyOptions.options()
                .returnNew(true)
                .upsert(true);
        return this.mongoTemplate.findAndModify(query, update, options, Article.class);
    }

}
