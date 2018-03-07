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
public class Contrib {
    private String surname;
    private String givenName;
    private String surnameEn;
    private String givenNameEn;
    private String bio;
    private String email;
    private List<String> affIds;
}
