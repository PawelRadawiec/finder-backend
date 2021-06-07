package com.info.finder.service;

import com.info.finder.model.Article;
import com.info.finder.model.Comment;
import com.info.finder.model.Message;
import com.info.finder.model.Rating;
import com.info.finder.repository.sequence.NextSequenceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class CommentService {

    private ArticleService articleService;
    private NextSequenceService nextSequenceService;
    private CommentMessageService messageService;

    public CommentService(ArticleService articleService, NextSequenceService nextSequenceService, CommentMessageService messageService) {
        this.articleService = articleService;
        this.nextSequenceService = nextSequenceService;
        this.messageService = messageService;
    }

    public Article add(String articleId, Comment comment) {
        Article article = articleService.getById(articleId);
        comment.setId(nextSequenceService.getNextSequence(Comment.SEQUENCE_NAME));
        comment.setAuthor(SystemUserHelper.username());
        comment.setShortText(substringText(comment.getText()));
        comment.setRatings(new ArrayList<>());
        article.getComments().add(comment);
        messageService.send(new Message(
                article.getAuthor(),
                comment.getAuthor(),
                String.format("Comment from %s. %s", comment.getAuthor(), comment.getShortText())
        ));
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

    public Comment getCommentById(Long commentId, Article article) {
        return article.getComments().stream()
                .filter(c -> c.getId() == commentId)
                .findAny()
                .orElse(null);
    }

    public boolean containsRating(Comment comment, Rating rating) {
        return comment.getRatings().stream()
                .filter(r -> r.getAuthor().equals(rating.getAuthor()))
                .findAny()
                .orElse(null) != null;
    }

}
