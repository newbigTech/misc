package com.newbig.scopus.model;

import javax.persistence.*;

@Table(name = "scopus_2016")
public class Scopus2016 {
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
}