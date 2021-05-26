package com.info.finder.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Message {

    private String to;
    private String from;
    private String message;

}
