package com.newbig.scopus;

public class Result {
    private Author author;
    private String realName;
    private String realAffiliation;
    private String hIndex;
    private String authorId;
    private String url;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getRealAffiliation() {
        return realAffiliation;
    }

    public void setRealAffiliation(String realAffiliation) {
        this.realAffiliation = realAffiliation;
    }

    public String gethIndex() {
        return hIndex;
    }

    public void sethIndex(String hIndex) {
        this.hIndex = hIndex;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
