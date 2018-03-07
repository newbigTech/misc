package com.newbig.word2xml.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Reference {
    private String label;
    private ElementCitation citationCh;
    private ElementCitation citationEn;
    //下面这两个是引用硕士博士 论文的时候
    private String note;
    private String noteEn;
}
