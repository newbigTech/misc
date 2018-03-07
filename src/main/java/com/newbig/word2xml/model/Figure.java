package com.newbig.word2xml.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.newbig.word2xml.utils.Constants.FIG;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Figure {
    private String id;
    private String figureNameCh;
    private String figureNameEn;
    private String figureLink;

    public String toString(){
        return FIG.replace("${id}",id)
                .replace("${figureNameCh}",figureNameCh)
                .replace("${figureNameEn}",figureNameEn)
                .replace("${figureLink}",figureLink)
                ;
    }
}
