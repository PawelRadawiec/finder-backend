package com.info.finder.model;

import lombok.Data;

import javax.validation.Valid;
import java.util.List;

@Data
public class ArticleRegistration {

    @Valid
    private Article article;
    private ArticleStep currentStep;
    private TargetStep targetStep;
    private List<Buttons> buttonsVisibility;

}
