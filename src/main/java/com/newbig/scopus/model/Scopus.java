package com.newbig.scopus.model;

import javax.persistence.*;

public class Scopus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    private String abbr;

    @Column(name = "last_name")
    private String lastName;

    private String category;

    @Column(name = "primary_affiliation")
    private String primaryAffiliation;

    private String country;

    @Column(name = "scopus_name")
    private String scopusName;

    @Column(name = "scopus_affiliation")
    private String scopusAffiliation;

    @Column(name = "scopus_hindex")
    private String scopusHindex;

    @Column(name = "scopus_author_id")
    private String scopusAuthorId;

    @Column(name = "scopus_scores")
    private String scopusScores;

    @Column(name = "gate_research_items")
    private String gateResearchItems;

    @Column(name = "gate_reads")
    private String gateReads;

    @Column(name = "gate_citations")
    private String gateCitations;

    @Column(name = "gate_rg_score")
    private String gateRgScore;

    @Column(name = "gate_hindex")
    private String gateHindex;

    @Column(name = "scopus_article_2016")
    private String scopusArticle2016;

    private Integer status;

    private String s2013;

    private String s2014;

    private String s2015;

    private String s2016;

    private String s2017;

    private String lt2013;

    private String subtotal;

    private String gt2017;

    private Long total;

    @Column(name = "scopus_url")
    private String scopusUrl;

    @Column(name = "s2013_before")
    private String s2013Before;

    @Column(name = "s2014_before")
    private String s2014Before;

    @Column(name = "s2015_before")
    private String s2015Before;

    @Column(name = "s2016_before")
    private String s2016Before;

    @Column(name = "s2017_before")
    private String s2017Before;

    @Column(name = "lt2013_before")
    private String lt2013Before;

    @Column(name = "subtotal_before")
    private String subtotalBefore;

    @Column(name = "gt2017_before")
    private String gt2017Before;

    @Column(name = "total_before")
    private Long totalBefore;

    private String datetime;

    @Column(name = "scopus_article_2016_1")
    private String scopusArticle20161;

    @Column(name = "scopus_article_2016_2")
    private String scopusArticle20162;

    @Column(name = "scopus_scores_before")
    private String scopusScoresBefore;

    @Column(name = "check_result")
    private Integer checkResult;

    @Column(name = "scopus_hindex_before")
    private String scopusHindexBefore;

    @Column(name = "gate_status")
    private Integer gateStatus;

    @Column(name = "gate_author_home")
    private String gateAuthorHome;

    @Column(name = "gate_name")
    private String gateName;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return first_name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return abbr
     */
    public String getAbbr() {
        return abbr;
    }

    /**
     * @param abbr
     */
    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    /**
     * @return last_name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * @return primary_affiliation
     */
    public String getPrimaryAffiliation() {
        return primaryAffiliation;
    }

    /**
     * @param primaryAffiliation
     */
    public void setPrimaryAffiliation(String primaryAffiliation) {
        this.primaryAffiliation = primaryAffiliation;
    }

    /**
     * @return country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return scopus_name
     */
    public String getScopusName() {
        return scopusName;
    }

    /**
     * @param scopusName
     */
    public void setScopusName(String scopusName) {
        this.scopusName = scopusName;
    }

    /**
     * @return scopus_affiliation
     */
    public String getScopusAffiliation() {
        return scopusAffiliation;
    }

    /**
     * @param scopusAffiliation
     */
    public void setScopusAffiliation(String scopusAffiliation) {
        this.scopusAffiliation = scopusAffiliation;
    }

    /**
     * @return scopus_hindex
     */
    public String getScopusHindex() {
        return scopusHindex;
    }

    /**
     * @param scopusHindex
     */
    public void setScopusHindex(String scopusHindex) {
        this.scopusHindex = scopusHindex;
    }

    /**
     * @return scopus_author_id
     */
    public String getScopusAuthorId() {
        return scopusAuthorId;
    }

    /**
     * @param scopusAuthorId
     */
    public void setScopusAuthorId(String scopusAuthorId) {
        this.scopusAuthorId = scopusAuthorId;
    }

    /**
     * @return scopus_scores
     */
    public String getScopusScores() {
        return scopusScores;
    }

    /**
     * @param scopusScores
     */
    public void setScopusScores(String scopusScores) {
        this.scopusScores = scopusScores;
    }

    /**
     * @return gate_research_items
     */
    public String getGateResearchItems() {
        return gateResearchItems;
    }

    /**
     * @param gateResearchItems
     */
    public void setGateResearchItems(String gateResearchItems) {
        this.gateResearchItems = gateResearchItems;
    }

    /**
     * @return gate_reads
     */
    public String getGateReads() {
        return gateReads;
    }

    /**
     * @param gateReads
     */
    public void setGateReads(String gateReads) {
        this.gateReads = gateReads;
    }

    /**
     * @return gate_citations
     */
    public String getGateCitations() {
        return gateCitations;
    }

    /**
     * @param gateCitations
     */
    public void setGateCitations(String gateCitations) {
        this.gateCitations = gateCitations;
    }

    /**
     * @return gate_rg_score
     */
    public String getGateRgScore() {
        return gateRgScore;
    }

    /**
     * @param gateRgScore
     */
    public void setGateRgScore(String gateRgScore) {
        this.gateRgScore = gateRgScore;
    }

    /**
     * @return gate_hindex
     */
    public String getGateHindex() {
        return gateHindex;
    }

    /**
     * @param gateHindex
     */
    public void setGateHindex(String gateHindex) {
        this.gateHindex = gateHindex;
    }

    /**
     * @return scopus_article_2016
     */
    public String getScopusArticle2016() {
        return scopusArticle2016;
    }

    /**
     * @param scopusArticle2016
     */
    public void setScopusArticle2016(String scopusArticle2016) {
        this.scopusArticle2016 = scopusArticle2016;
    }

    /**
     * @return status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return s2013
     */
    public String getS2013() {
        return s2013;
    }

    /**
     * @param s2013
     */
    public void setS2013(String s2013) {
        this.s2013 = s2013;
    }

    /**
     * @return s2014
     */
    public String getS2014() {
        return s2014;
    }

    /**
     * @param s2014
     */
    public void setS2014(String s2014) {
        this.s2014 = s2014;
    }

    /**
     * @return s2015
     */
    public String getS2015() {
        return s2015;
    }

    /**
     * @param s2015
     */
    public void setS2015(String s2015) {
        this.s2015 = s2015;
    }

    /**
     * @return s2016
     */
    public String getS2016() {
        return s2016;
    }

    /**
     * @param s2016
     */
    public void setS2016(String s2016) {
        this.s2016 = s2016;
    }

    /**
     * @return s2017
     */
    public String getS2017() {
        return s2017;
    }

    /**
     * @param s2017
     */
    public void setS2017(String s2017) {
        this.s2017 = s2017;
    }

    /**
     * @return lt2013
     */
    public String getLt2013() {
        return lt2013;
    }

    /**
     * @param lt2013
     */
    public void setLt2013(String lt2013) {
        this.lt2013 = lt2013;
    }

    /**
     * @return subtotal
     */
    public String getSubtotal() {
        return subtotal;
    }

    /**
     * @param subtotal
     */
    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    /**
     * @return gt2017
     */
    public String getGt2017() {
        return gt2017;
    }

    /**
     * @param gt2017
     */
    public void setGt2017(String gt2017) {
        this.gt2017 = gt2017;
    }

    /**
     * @return total
     */
    public Long getTotal() {
        return total;
    }

    /**
     * @param total
     */
    public void setTotal(Long total) {
        this.total = total;
    }

    /**
     * @return scopus_url
     */
    public String getScopusUrl() {
        return scopusUrl;
    }

    /**
     * @param scopusUrl
     */
    public void setScopusUrl(String scopusUrl) {
        this.scopusUrl = scopusUrl;
    }

    /**
     * @return s2013_before
     */
    public String getS2013Before() {
        return s2013Before;
    }

    /**
     * @param s2013Before
     */
    public void setS2013Before(String s2013Before) {
        this.s2013Before = s2013Before;
    }

    /**
     * @return s2014_before
     */
    public String getS2014Before() {
        return s2014Before;
    }

    /**
     * @param s2014Before
     */
    public void setS2014Before(String s2014Before) {
        this.s2014Before = s2014Before;
    }

    /**
     * @return s2015_before
     */
    public String getS2015Before() {
        return s2015Before;
    }

    /**
     * @param s2015Before
     */
    public void setS2015Before(String s2015Before) {
        this.s2015Before = s2015Before;
    }

    /**
     * @return s2016_before
     */
    public String getS2016Before() {
        return s2016Before;
    }

    /**
     * @param s2016Before
     */
    public void setS2016Before(String s2016Before) {
        this.s2016Before = s2016Before;
    }

    /**
     * @return s2017_before
     */
    public String getS2017Before() {
        return s2017Before;
    }

    /**
     * @param s2017Before
     */
    public void setS2017Before(String s2017Before) {
        this.s2017Before = s2017Before;
    }

    /**
     * @return lt2013_before
     */
    public String getLt2013Before() {
        return lt2013Before;
    }

    /**
     * @param lt2013Before
     */
    public void setLt2013Before(String lt2013Before) {
        this.lt2013Before = lt2013Before;
    }

    /**
     * @return subtotal_before
     */
    public String getSubtotalBefore() {
        return subtotalBefore;
    }

    /**
     * @param subtotalBefore
     */
    public void setSubtotalBefore(String subtotalBefore) {
        this.subtotalBefore = subtotalBefore;
    }

    /**
     * @return gt2017_before
     */
    public String getGt2017Before() {
        return gt2017Before;
    }

    /**
     * @param gt2017Before
     */
    public void setGt2017Before(String gt2017Before) {
        this.gt2017Before = gt2017Before;
    }

    /**
     * @return total_before
     */
    public Long getTotalBefore() {
        return totalBefore;
    }

    /**
     * @param totalBefore
     */
    public void setTotalBefore(Long totalBefore) {
        this.totalBefore = totalBefore;
    }

    /**
     * @return datetime
     */
    public String getDatetime() {
        return datetime;
    }

    /**
     * @param datetime
     */
    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    /**
     * @return scopus_article_2016_1
     */
    public String getScopusArticle20161() {
        return scopusArticle20161;
    }

    /**
     * @param scopusArticle20161
     */
    public void setScopusArticle20161(String scopusArticle20161) {
        this.scopusArticle20161 = scopusArticle20161;
    }

    /**
     * @return scopus_article_2016_2
     */
    public String getScopusArticle20162() {
        return scopusArticle20162;
    }

    /**
     * @param scopusArticle20162
     */
    public void setScopusArticle20162(String scopusArticle20162) {
        this.scopusArticle20162 = scopusArticle20162;
    }

    /**
     * @return scopus_scores_before
     */
    public String getScopusScoresBefore() {
        return scopusScoresBefore;
    }

    /**
     * @param scopusScoresBefore
     */
    public void setScopusScoresBefore(String scopusScoresBefore) {
        this.scopusScoresBefore = scopusScoresBefore;
    }

    /**
     * @return check_result
     */
    public Integer getCheckResult() {
        return checkResult;
    }

    /**
     * @param checkResult
     */
    public void setCheckResult(Integer checkResult) {
        this.checkResult = checkResult;
    }

    /**
     * @return scopus_hindex_before
     */
    public String getScopusHindexBefore() {
        return scopusHindexBefore;
    }

    /**
     * @param scopusHindexBefore
     */
    public void setScopusHindexBefore(String scopusHindexBefore) {
        this.scopusHindexBefore = scopusHindexBefore;
    }

    /**
     * @return gate_status
     */
    public Integer getGateStatus() {
        return gateStatus;
    }

    /**
     * @param gateStatus
     */
    public void setGateStatus(Integer gateStatus) {
        this.gateStatus = gateStatus;
    }

    /**
     * @return gate_author_home
     */
    public String getGateAuthorHome() {
        return gateAuthorHome;
    }

    /**
     * @param gateAuthorHome
     */
    public void setGateAuthorHome(String gateAuthorHome) {
        this.gateAuthorHome = gateAuthorHome;
    }

    /**
     * @return gate_name
     */
    public String getGateName() {
        return gateName;
    }

    /**
     * @param gateName
     */
    public void setGateName(String gateName) {
        this.gateName = gateName;
    }
}