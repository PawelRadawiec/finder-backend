package com.info.finder.repository.sequence;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "customSequences")
public class CustomSequence {
    @Id
    private String id;
    private long seq;

}
