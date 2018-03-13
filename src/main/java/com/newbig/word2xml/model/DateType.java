package com.newbig.word2xml.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class DateType {
    private String year;
    private String month;
    private String day;

    public DateType(){
        this.year ="";
        this.month="";
        this.day ="";
    }
}
