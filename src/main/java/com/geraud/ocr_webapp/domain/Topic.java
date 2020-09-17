package com.geraud.ocr_webapp.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter

public class Topic {

    private Long id_topic;

    private String keyword;

    private Set<Book> books;
}
