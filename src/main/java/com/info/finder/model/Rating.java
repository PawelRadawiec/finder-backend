package com.info.finder.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class Rating {

    @Id
    private String author;
    private boolean value;

}
