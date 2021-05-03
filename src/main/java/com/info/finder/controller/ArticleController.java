package com.info.finder.controller;

import com.info.finder.model.Article;
import com.info.finder.model.ArticleRegistration;
import com.info.finder.service.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/article/")
@CrossOrigin("*")
public class ArticleController {

    private ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping(value = "registration")
    public ResponseEntity<ArticleRegistration> create(@Valid @RequestBody ArticleRegistration registration) {
        return new ResponseEntity<>(articleService.create(registration), HttpStatus.OK);
    }

    @GetMapping(value = "search")
    public ResponseEntity<List<Article>> search() {
        return new ResponseEntity<>(articleService.search(), HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<Article> getById(@PathVariable String id) {
        return new ResponseEntity<>(articleService.getById(id), HttpStatus.OK);
    }

    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        articleService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
