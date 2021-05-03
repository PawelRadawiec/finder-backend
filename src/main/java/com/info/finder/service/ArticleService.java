package com.info.finder.service;

import com.info.finder.model.*;
import com.info.finder.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class ArticleService {

    private ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public ArticleRegistration create(ArticleRegistration registration) {
        TargetStep target = registration.getTargetStep();
        if (registration.getCurrentStep() == null) {
            registration.setCurrentStep(ArticleStep.NO_STEP);
            return registration;
        }
        if (target == null) {
            registration.setTargetStep(TargetStep.NO_TARGET);
            return registration;
        }
        switch (target) {
            case NEXT -> handleNext(registration);
            case BACK -> handleBack(registration);
            default -> {
                return registration;
            }
        }
        return registration;
    }

    public Article create(Article article) {
        article.setAuthor(SystemUserHelper.username());
        return articleRepository.save(article);
    }

    public List<Article> search() {
        return articleRepository.findAll();
    }

    public Article getById(String id) {
        return articleRepository.findById(id).orElseGet(Article::new);
    }

    public void deleteById(String id) {
        articleRepository.deleteById(id);
    }

    private void handleNext(ArticleRegistration registration) {
        switch (registration.getCurrentStep()) {
            case DATA -> {
                registration.setCurrentStep(ArticleStep.SUMMARY);
                registration.setButtonsVisibility(Arrays.asList(Buttons.NEXT, Buttons.BACK));
            }
            case SUMMARY -> {
                registration.setCurrentStep(ArticleStep.DONE);
                registration.setButtonsVisibility(Arrays.asList(Buttons.SAVE, Buttons.BACK));
            }
            case DONE -> {
                Article article = registration.getArticle();
                article.setAuthor(SystemUserHelper.username());
                articleRepository.save(article);
                registration.setCurrentStep(ArticleStep.DONE);
            }
            default -> registration.setCurrentStep(ArticleStep.NO_STEP);
        }
    }

    private void handleBack(ArticleRegistration registration) {
        switch (registration.getCurrentStep()) {
            case DATA, SUMMARY -> {
                registration.setCurrentStep(ArticleStep.DATA);
                registration.setButtonsVisibility(Collections.singletonList(Buttons.NEXT));
            }
            case DONE -> {
                registration.setCurrentStep(ArticleStep.SUMMARY);
                registration.setButtonsVisibility(Arrays.asList(Buttons.NEXT, Buttons.BACK));
            }
            default -> registration.setCurrentStep(ArticleStep.NO_STEP);
        }
    }

}
