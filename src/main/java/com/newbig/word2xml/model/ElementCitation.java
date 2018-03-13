package com.newbig.word2xml.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ElementCitation {
    private List<Name> personGroup;
    private String articleTitle;
    private String source;
    private String year;
    private String volume;
    private String issue;
    private String fpage;
    private String lpage;
    private String uri;
    private String annotation;
}
