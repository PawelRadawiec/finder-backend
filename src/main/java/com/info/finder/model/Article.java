package com.info.finder.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Document
public class Article {

    @Id
    private String id;

    @NotEmpty
    private String url;

    @NotEmpty
    private String title;
    private String author;

    @NotEmpty
    private String pictureUrl;

    @NotEmpty
    private String description;
    
    @NotEmpty
    private List<String> tags;

    List<Comment> comments;

    public List<String> getTags() {
        if (tags == null) {
            tags = new ArrayList<>();
        }
        return tags;
    }

    public List<Comment> getComments() {
        if (comments == null) {
            comments = new ArrayList<>();
        }
        return comments;
    }
}
