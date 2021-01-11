package com.info.finder.service;

import com.info.finder.model.Article;
import com.info.finder.model.Comment;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private ArticleService articleService;

    public CommentService(ArticleService articleService) {
        this.articleService = articleService;
    }

    public Article add(String articleId, Comment comment) {
        Article article = articleService.getById(articleId);
        comment.setAuthor("john2321");
        comment.setShortText(substringText(comment.getText()));
        article.getComments().add(comment);
        return articleService.create(article);
    }

    private String substringText(String text) {
        String shortText = "";
        if (StringUtils.isEmpty(text)) {
            return shortText;
        }
        int length = text.length();
        shortText = length <= 80 ? text : text.substring(0 , length / 2) + "...";
        return shortText;
    }

}
