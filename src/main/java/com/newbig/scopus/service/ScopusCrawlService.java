package com.newbig.scopus.service;


import com.alibaba.fastjson.JSON;
import com.newbig.scopus.mapper.ScopusMapper;
import com.newbig.scopus.model.Author;
import com.newbig.scopus.model.Result;
import com.newbig.scopus.model.Scopus;
import com.newbig.scopus.service.ScopusService;
import com.newbig.scopus.utils.FileTxtUtil;
import com.newbig.scopus.utils.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.assertj.core.util.Lists;
import org.joda.time.DateTime;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ScopusCrawlService {
    @Autowired
    private ScopusService scopusService;
    @Autowired
    private ScopusMapper scopusMapper;

    public void schedule() throws InterruptedException {
//        System.setProperty("webdriver.chrome.driver","D:\\MyPF\\chromedriver_win32\\chromedriver.exe");
        System.setProperty("webdriver.chrome.driver","/Users/haibo/Documents/soft/chromedriver");
        WebDriver driver = null;

        List<Scopus> scopuses = scopusService.getScopusByStatus(3);
        driver= new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1260, 800));
        driver.manage().getCookies().clear();
        for(Scopus scopus: scopuses) {
            log.info("id={}",scopus.getId());
            try {
                String afflication = getAfflication(scopus.getPrimaryAffiliation());
                search(driver,scopus.getFirstName(),scopus.getLastName(),afflication);
                Thread.sleep(100);
                if(driver.getPageSource().contains("未找到作者")){
                    log.info("1 - {},{},{} 未找到作者",scopus.getFirstName(),scopus.getLastName(),afflication);
//                    String[] ss  = afflication.split(" ");
//                    String max = ss[0];
//                    for(String s:ss){
//                        if(s.length()>max.length()){
//                            max = s;
//                        }
//                    }
//                    afflication = max;
                    search(driver,scopus.getAbbr(),scopus.getLastName(),afflication);
                    Thread.sleep(100);
                    if(driver.getPageSource().contains("未找到作者")){
                        log.info("2 - {},{},{} 未找到作者",scopus.getAbbr(),scopus.getLastName(),afflication);
                        search(driver,scopus.getAbbr().split("\\.")[0],scopus.getLastName(),afflication);
                        Thread.sleep(100);
                        if(driver.getPageSource().contains("未找到作者")){
                            log.info("3 - {},{},{} 未找到作者",scopus.getAbbr().split("\\.")[0],scopus.getLastName(),scopus.getPrimaryAffiliation());
                            scopus.setStatus(-1);
                            scopusMapper.updateByPrimaryKeySelective(scopus);
                            continue;
                        }
                    }

                }
                Page page = new Page();
                page.setHtml(new Html(driver.getPageSource()));
                List<String> authors = page.getHtml().xpath("//*[@class='authorResultsNamesCol col20']/a/text()").all();
                List<String> affiliations = page.getHtml().xpath("//*[@class='dataCol5']/text()").all();
                List<String> urls = page.getHtml().xpath("//*[@class='authorResultsNamesCol col20']/a/@href").all();
                driver.manage().getCookies().clear();
                log.info(urls.get(0));
                driver.get(urls.get(0));
                Thread.sleep(2000);

                page = new Page();
                page.setHtml(new Html(driver.getPageSource()));
                String name = page.getHtml().xpath("//*[@id=\"profileleftinside\"]/div[2]/div[1]/h1/text()").get();
                String school = page.getHtml().xpath("//*[@id=\"profileleftinside\"]/div[2]/div[1]/div[1]/text()").get();
                String authorId = page.getHtml().xpath("//*[@id=\"profileleftinside\"]/div[2]/div[1]/div[2]/text()").get();
                String hindex = page.getHtml().xpath("//*[@id=\"authLeftList\"]/li[3]/div[2]/span[1]/text()").get();
                scopus.setScopusAuthorId(authorId.replace("作者 ID: ",""));
                scopus.setScopusHindex(hindex);
                scopus.setScopusAffiliation(school);
                scopus.setScopusName(name);
                List<String> articles = getArticle(driver);
                if(articles.size()>0){
                    scopus.setScopusArticle2016(articles.get(0));
                }
                if(articles.size()>1){
                    scopus.setScopusArticle20161(articles.get(1));
                }
                if(articles.size()>2){
                    scopus.setScopusArticle20162(articles.get(2));
                }
                log.info("2016 articles"+JSON.toJSONString(articles));

                driver.manage().getCookies().clear();
                Thread.sleep(7000);
                JavascriptExecutor j1 = (JavascriptExecutor) driver;
                j1.executeScript("document.getElementById('authViewCitOver').setAttribute('href','javascript:void(0);'); ");
                j1.executeScript("document.getElementById('authViewCitOver').click(); ");
                Thread.sleep(8000);
                String indexs1 = getIndexs(driver);
                scopus.setScopusScoresBefore(indexs1);
                driver.manage().getCookies().clear();
                JavascriptExecutor j = (JavascriptExecutor) driver;
                j.executeScript("document.getElementById('excludeAuthorsBox').click(); ");
                Thread.sleep(2000);
                j.executeScript("document.getElementById('updateOverviewButtonOn').click(); ");
                Thread.sleep(10000);
                String indexs2 = getIndexs(driver);
                if(!Objects.equals(indexs1,indexs2)){
                    scopus.setScopusScores(indexs2);
                    scopus.setStatus(22);
                    log.info("index ok");
                }else{
                    scopus.setScopusScores(JSON.toJSONString(Lists.newArrayList()));
                    scopus.setStatus(23);
                    log.info("index fail");
                }
                scopus.setDatetime(new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
                scopus.setStatus(3);
            }
            try{
                scopusMapper.updateByPrimaryKey(scopus);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        driver.quit();
    }
    public String getIndexs(WebDriver driver){
        Page page1 = new Page();
        page1.setHtml(new Html(driver.getPageSource()));
        String authorId1 = page1.getHtml().xpath("//*[@id=\"authorIdText\"]/text()").get();
        String hIndex = page1.getHtml().xpath("//*[@id=\"hirsch_index_open\"]/text()").get();
       log.info(authorId1 + "===----===" + hIndex);
        List<String> aaa = page1.getHtml().xpath("//*[@class='anchorText secondaryLink']/strong/text()").all();
        return JSON.toJSONString(aaa);
    }
    public void search(WebDriver driver,String first,String last,String affiliation){
        driver.get("https://www.scopus.com/freelookup/form/author.uri?zone=&origin=NO%20ORIGIN%20DEFINED");
        WebElement lastName = driver.findElement(By.id("lastname"));
        lastName.clear();
        lastName.sendKeys(last);
        WebElement firstName = driver.findElement(By.id("firstname"));
        firstName.clear();
        firstName.sendKeys(first);
        WebElement institute = driver.findElement(By.id("institute"));
        institute.clear();
        institute.sendKeys(affiliation.trim());
        driver.manage().getCookies().clear();
//        WebElement exactSearch = driver.findElement(By.id("exactSearch"));
//        exactSearch.click();
        JavascriptExecutor j = (JavascriptExecutor) driver;
        j.executeScript("document.getElementById('exactSearch').click(); ");
        WebElement authorSubmitBtn = driver.findElement(By.id("authorSubmitBtn"));
        authorSubmitBtn.sendKeys(Keys.ENTER);
    }
    private String getAfflication(String affiliation){
        if(affiliation.contains("Natl Inst Hlth (NIH) - USA")){
            affiliation="NIH";
        }else if(affiliation.contains("USDA (US Dept Agr)")){
            affiliation="USDA";
        }else if(affiliation.contains("NCAR (Natl Ctr Atmospheric Res)")){
            affiliation="NCAR";
        }else if(affiliation.contains("NOAA")){
            affiliation="NOAA";
        }else if(affiliation.contains("NIBIO")){
            affiliation="NIBIO";
        }else if(affiliation.contains("NCI")){
            affiliation="NCI";
        }else if(affiliation.contains("(NIA)")){
            affiliation="NIA";
        }else if(affiliation.contains("- JGI")){
            affiliation="JGI";
        }else {
//            affiliation = affiliation.replace("University","")
//                    .replace("university","").trim();
            affiliation = affiliation.split("\\(")[0].trim();
//            affiliation = affiliation.replace("Inst", "")
//                    .replace("Technol", "").replace("\"", "")
//                    .replace("&", "").replace("Company", "")
//                    .replace("Sci", "").replace("Natl", "")
//                    .replace("Sch", "").replace("Ctr", "")
//                    .replace("Res", "").replace("Agr", "")
//                    .replace("Calif", "").replace("Tech", "")
//                    .replace("Coll", "").replace("Max", "")
//                    .replace("Mem", "").replace("Fed", "")
//                    .replace("Agri", "").replace("Bioinformat", "")
//                    .replace("Med", "").replace("Lab", "")
//                    .replace("Hlth", "").replace("Bio", "")
//                    .replace("","").replace("&","")
            ;
        }
        return affiliation;
    }
    public List<String> getArticle(WebDriver driver) throws InterruptedException {
        for(Integer i=1;i<10;i++) {
            Thread.sleep(1000);
            if (i > 1) {
                JavascriptExecutor j1 = (JavascriptExecutor) driver;
                j1.executeScript("document.getElementById('nextPage').click(); ");
                Thread.sleep(1000);
            }
            Page page = new Page();
            page.setHtml(new Html(driver.getPageSource()));
            List<String> years = page.getHtml().xpath("//*[@class=\"dataCol4\"]/span/text()").all();
            List<String> titles = page.getHtml().xpath("//*[@class=\"dataCol2\"]/span/a/text()").all();
            years = years.stream().map(y -> y.trim()).collect(Collectors.toList());
//        log.info(JSON.toJSONString(years));
            if (CollectionUtils.isNotEmpty(years)) {
                Integer index;
                if (Integer.valueOf(years.get(0).trim()) < 2016) {
                    index = 0;
                } else if (years.indexOf("2016") != -1) {
                    index = years.indexOf("2016");
                } else if (years.indexOf("2015") != -1) {
                    index = years.indexOf("2015");
                } else if (years.indexOf("2014") != -1) {
                    index = years.indexOf("2014");
                } else if (years.indexOf("2013") != -1) {
                    index = years.indexOf("2013");
                } else if (years.indexOf("2012") != -1) {
                    index = years.indexOf("2012");
                } else if (years.indexOf("2011") != -1) {
                    index = years.indexOf("2011");
                } else if (years.indexOf("2010") != -1) {
                    index = years.indexOf("2010");
                } else if (years.indexOf("2009") != -1) {
                    index = years.indexOf("2009");
                } else if (years.indexOf("2008") != -1) {
                    index = years.indexOf("2008");
                } else {
                    continue;
                }
                if(titles.size()<index+3){
                    return titles.subList(index,index+1);
                }else {
                    return titles.subList(index, index + 3);
                }
            }
        }
        return Lists.newArrayList();
    }
}
