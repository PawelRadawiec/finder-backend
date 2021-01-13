package com.info.finder.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
public class Comment {

    @Transient
    public static final String SEQUENCE_NAME = "comments_sequence";

    @Id
    private long id;

    private String author;
    @NotEmpty()
    private String text;
    private String shortText;
    private List<Rating> ratings;

    public List<Rating> getRatings() {
        return ratings;
    }
}
