package com.info.finder.service;

import com.info.finder.model.Article;
import com.info.finder.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    private ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Article create(Article article) {
        return articleRepository.save(article);
    }

    public List<Article> search() {
        return articleRepository.findAll();
    }

}
