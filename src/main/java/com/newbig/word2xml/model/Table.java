package com.newbig.word2xml.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Table {
    private Integer id;
    private String titleCh;
    private String titleEn;
    private String html;
}
