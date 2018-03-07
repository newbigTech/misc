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
public class ArticleMeta {
    private String publisherId;
    private String artAccessId;
    private String doi;
    private String other;
    private String subject;
    private String subjectEn;
    private String articleTitle;
    private String transTitle;
    private List<Contrib> contribGroup;
    private List<Aff> affs;
    private DateType pubDate;
    private String volume;
    private String issue;
    private String fpage;
    private String lpage;
    private DateType receivedDate;
    private Permissions permissions;
    private String abstractCh;
    private String abstractEn;
    private List<String> kwdGroupCh;
    private List<String> kwdGroupEn;
    private FundingGroup fundingGroup;


    private List<Reference> references;
}
