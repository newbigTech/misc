package com.newbig.scopus.service;


import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.newbig.scopus.mapper.ScopusMapper;
import com.newbig.scopus.model.Scopus;
import com.newbig.scopus.utils.SimilarityUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.text.similarity.JaccardSimilarity;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class ResearchGateService {
    @Autowired
    private ScopusService scopusService;
    @Autowired
    private ScopusMapper scopusMapper;

    public void schedule() throws InterruptedException {
        System.out.println("Seting browser");
        System.setProperty("webdriver.chrome.driver","/Users/haibo/Documents/soft/chromedriver");

//        System.setProperty("webdriver.chrome.driver", "D:\\MyPF\\chromedriver_win32\\chromedriver.exe");
        WebDriver driver = null;

        List<Scopus> scopuses = scopusService.getScopusByStatus(32);
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(20L, TimeUnit.MINUTES);
        driver.manage().window().setSize(new Dimension(1260, 800));
        driver.get("https://www.researchgate.net/login");
        WebElement inputLogin = driver.findElement(By.id("input-login"));
        inputLogin.sendKeys("");
        // inputLogin.sendKeys("");
        WebElement inputPassword = driver.findElement(By.id("input-password"));
        inputPassword.sendKeys("");
        //inputPassword.sendKeys("");
        WebElement authorSubmitBtn = driver.findElement(By.className("nova-c-button--align-center"));
        authorSubmitBtn.sendKeys(Keys.ENTER);
        Thread.sleep(1000);
//        driver.manage().getCookies().clear();
        for (Scopus scopus : scopuses) {
            log.info(scopus.getId().toString());
            try {
                String url =getUrl(driver,scopus.getScopusArticle2016());
                    if(url == null){
                        url = getUrl(driver,scopus.getScopusArticle20161());
                        if(url == null){
                            url = getUrl(driver,scopus.getScopusArticle20162());
                            if(url == null){
                                log.info("not found ");
                                scopus.setGateStatus(0);
                                scopusMapper.updateByPrimaryKey(scopus);
                                continue;
                            }
                        }
                    }
                log.info(JSON.toJSONString(url));
                driver.get("https://www.researchgate.net/" + url);
                Thread.sleep(1000);
                try {
                    WebElement more = driver.findElement(By.className("show-more-authors"));
                    more.click();
                    Thread.sleep(16000);
                }catch (Exception e){
                }
                Page page = new Page();
                page.setHtml(new Html(driver.getPageSource()));
                List<String> names = page.getHtml().xpath("//*[@class=\"nova-e-link nova-e-link--color-inherit nova-e-link--theme-bare\"]/text()").all();
                List<String> gateUrls = page.getHtml().xpath("//*[@class=\"nova-e-link nova-e-link--color-inherit nova-e-link--theme-bare\"]/@href").all();
                log.info(JSON.toJSONString(names));
                for (int i = 0; i < names.size(); i++) {
                    if (SimilarityUtils.calculateSimilarityLCS(names.get(i), scopus.getScopusName())) {
                        log.info("scopusName:{},gateName:{}",scopus.getScopusName(),names.get(i));
                        String gateUrl =  gateUrls.get(i);
                        if(!gateUrl.contains("scientific-contributions")) {
                            driver.get(gateUrl);
                            scopus.setGateAuthorHome(gateUrl);
                            Thread.sleep(1000);
                            page.setHtml(new Html(driver.getPageSource()));
                            List<String> a = page.getHtml().xpath("//*[@class=\"nova-e-text nova-e-text--size-xl nova-e-text--family-sans-serif nova-e-text--spacing-xxs profile-highlights-stats__counter\"]/text()").all();
                            log.info(JSON.toJSONString(a));
                            scopus.setGateName(names.get(i));
                            scopus.setGateResearchItems(a.get(0));
                            scopus.setGateReads(a.get(1));
                            scopus.setGateCitations(a.get(2));
                            WebElement rgscore = driver.findElement(By.className("ga-rgscore"));
                            rgscore.click();
                            Thread.sleep(3000);
                            page.setHtml(new Html(driver.getPageSource()));
                            List<String> rbscore = page.getHtml().xpath("//*[@class=\"number\"]/text()").all();
                            log.info(JSON.toJSONString(rbscore));
                            if (rbscore.size() == 3) {
                                scopus.setGateRgScore(rbscore.get(0));
                                scopus.setGateHindex(rbscore.get(2));
                            } else {
                                scopus.setGateRgScore(rbscore.get(1));
                                scopus.setGateHindex(rbscore.get(3));
                            }
                            scopus.setGateStatus(1);
                        }
                        break;
                    }
                }
                if(scopus.getGateStatus() != 1) {
                    scopus.setGateStatus(3);
                }
            } catch (Exception e) {
                e.printStackTrace();
                scopus.setGateStatus(2);
            }
            try {
                scopusMapper.updateByPrimaryKey(scopus);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        driver.quit();


    }

    public Integer checkArticle(List<String> articles,String scopusArticle){
        for(Integer i=0;i<articles.size();i++) {
            JaccardSimilarity jaccardSimilarity = new JaccardSimilarity();
            double jcdsimilary = jaccardSimilarity.apply(articles.get(i).toLowerCase(), scopusArticle.toLowerCase());
            System.out.println(articles.get(i));
            System.out.println(scopusArticle);
            log.info(jcdsimilary + "");
            if (jcdsimilary > 0.85) {
                return i;
            }
        }
        return null;
    }
    public String getUrl(WebDriver driver, String article){
        driver.get("https://www.researchgate.net/search.Search.html?type=publication&query=" + article);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Page page = new Page();
        page.setHtml(new Html(driver.getPageSource()));
        List<String> articles = page.getHtml().xpath("//*[@class=\"nova-e-link nova-e-link--color-inherit nova-e-link--theme-bare\"]/text()").all();
       Integer index = checkArticle(articles,article);
        if(null != index){
            List<String> urls = page.getHtml().xpath("//*[@class=\"nova-e-link nova-e-link--color-inherit nova-e-link--theme-bare\"]/@href").all();
            if(urls.get(index).startsWith("publication")){
                return urls.get(index);
            }
        }
        return null;

    }
}
