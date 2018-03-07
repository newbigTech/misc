package com.newbig.word2xml.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Sec {
    private String id;
    private String label;
    private String title;
//    private List<Object> content;//paragraph figure table equitation
//    private Sec child;

    public String toString(){
        return "";
    }
}
