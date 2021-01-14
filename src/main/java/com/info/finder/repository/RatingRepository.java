package com.info.finder.repository;

import com.info.finder.model.Article;
import com.info.finder.model.Rating;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class RatingRepository {

    private MongoTemplate mongoTemplate;

    public RatingRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * it will update rating only if exist in comment
     * @param articleId
     * @param commentId
     * @param rating
     * @return
     */
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
