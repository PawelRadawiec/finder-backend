package com.info.finder.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SystemEmail {

    private String subject;
    private String message;
    private String from;
    private String sendTo;

}
