package com.newbig.word2xml.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JournalMeta {
    private String journalId;
    private String journalTitle;
    private String journalTitleEn;
    private String issn;
    private String publisherName;
    private String publisherLoc;
}
