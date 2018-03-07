package com.newbig.scopus;

public class Author {
    private String firstName;
    private String abbr;
    private String category;
    private String lastName;
    private String  affiliation;
    private String country;

    public Author(){}

    public Author(String firstName,String abbr,String lastName,String category,String affiliation,String country){
        this.firstName = firstName;
        this.lastName = lastName;
        this.affiliation = affiliation;
        this.abbr = abbr;
        this.category = category;
        this.country = country;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAffiliation() {
        return this.affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
