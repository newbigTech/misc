package com.newbig.word2xml.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.newbig.word2xml.utils.Constants.FORMULA;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Formula {
    private String id;
    private String formula;

    public String toString(){
        return FORMULA.replace("${id}",id)
                .replace("${formula}",formula);
    }
}
