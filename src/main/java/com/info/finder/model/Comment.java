package com.info.finder.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class Comment {

    private String author;
    @NotEmpty()
    private String text;
    private String shortText;

}
