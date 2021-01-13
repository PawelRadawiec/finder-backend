package com.info.finder.service;

import com.info.finder.model.Article;
import com.info.finder.model.Comment;
import com.info.finder.repository.sequence.NextSequenceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class CommentService {

    private ArticleService articleService;
    private NextSequenceService nextSequenceService;

    public CommentService(ArticleService articleService, NextSequenceService nextSequenceService) {
        this.articleService = articleService;
        this.nextSequenceService = nextSequenceService;
    }

    public Article add(String articleId, Comment comment) {
        Article article = articleService.getById(articleId);
        comment.setId(nextSequenceService.getNextSequence(Comment.SEQUENCE_NAME));
        comment.setAuthor("john2321");
        comment.setShortText(substringText(comment.getText()));
        comment.setRatings(new ArrayList<>());
        article.getComments().add(comment);
        return articleService.create(article);
    }

    private String substringText(String text) {
        String shortText = "";
        if (StringUtils.isEmpty(text)) {
            return shortText;
        }
        int length = text.length();
        shortText = length <= 40 ? text : text.substring(0, length / 2) + "...";
        return shortText;
    }

}
