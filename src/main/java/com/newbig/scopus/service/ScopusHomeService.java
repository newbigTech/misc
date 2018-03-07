package com.newbig.scopus.service;


import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.newbig.scopus.mapper.ScopusMapper;
import com.newbig.scopus.model.Scopus;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ScopusHomeService {
    @Autowired
    private ScopusService scopusService;
    @Autowired
    private ScopusMapper scopusMapper;

    public void schedule() throws InterruptedException {
        System.out.println("Seting browser");
        System.setProperty("webdriver.chrome.driver", "D:\\MyPF\\chromedriver_win32\\chromedriver.exe");
        List<Scopus> scopuses = scopusService.getScopusByStatus(1);
        WebDriver driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1260, 800));
        driver.manage().getCookies().clear();
        driver.get("https://www.scopus.com/authid/detail.uri?authorId=7403354075");
        Thread.sleep(1000);
        for (Scopus scopus : scopuses) {
            try {
                driver.get("https://www.scopus.com/authid/detail.uri?authorId=" + scopus.getScopusAuthorId());
//            driver.get("https://www.scopus.com/authid/detail.uri?authorId=7403354075");
                Thread.sleep(1000);
                for(Integer i=1;i<10;i++) {
                    Thread.sleep(1000);
                    if(i>1) {
                        JavascriptExecutor j1 = (JavascriptExecutor) driver;
                        j1.executeScript("document.getElementById('nextPage').click(); ");
                        Thread.sleep(1000);
                    }
                    Page page = new Page();
                    page.setHtml(new Html(driver.getPageSource()));
                    List<String> years = page.getHtml().xpath("//*[@class=\"dataCol4\"]/span/text()").all();
                    List<String> titles = page.getHtml().xpath("//*[@class=\"dataCol2\"]/span/a/text()").all();
                    years = years.stream().map(y -> y.trim()).collect(Collectors.toList());
                    log.info(JSON.toJSONString(years));
                    if(CollectionUtils.isNotEmpty(years)){
                        Integer index=null;
                        if(Integer.valueOf(years.get(0).trim())<2016){
                            index=0;
                        }else if(years.indexOf("2016")!=-1){
                            index = years.indexOf("2016");
                        }else if(years.indexOf("2015")!=-1){
                            index = years.indexOf("2015");
                        }else if(years.indexOf("2014")!=-1){
                            index = years.indexOf("2014");
                        }else if(years.indexOf("2013")!=-1){
                            index = years.indexOf("2013");
                        }else if(years.indexOf("2012")!=-1){
                            index = years.indexOf("2012");
                        }else if(years.indexOf("2011")!=-1){
                            index = years.indexOf("2011");
                        }else if(years.indexOf("2010")!=-1){
                            index = years.indexOf("2010");
                        }else if(years.indexOf("2009")!=-1){
                            index = years.indexOf("2009");
                        }else if(years.indexOf("2008")!=-1){
                            index = years.indexOf("2008");
                        }else{
                            continue;
                        }
                        scopus.setScopusArticle2016(titles.get(index));
                        scopus.setStatus(3);
                        log.info("===="+titles.get(index));
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                scopus.setStatus(4);
            }
            try {
                scopusMapper.updateByPrimaryKey(scopus);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        driver.quit();


    }
}