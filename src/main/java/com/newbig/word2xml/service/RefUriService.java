package com.newbig.word2xml.service;

import com.alibaba.fastjson.JSON;
import com.newbig.scopus.model.Scopus;
import com.newbig.word2xml.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;

import java.util.List;
@Slf4j
public class RefUriService {

    public static String getRefUri(String title,List<String> authors,List<String> keywords){
//        System.setProperty("webdriver.chrome.driver","/Users/haibo/Documents/soft/chromedriver");
        System.setProperty("webdriver.chrome.driver", "D:\\MyPF\\chromedriver_win32\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        WebDriver driver = new ChromeDriver(options);
//        String uri = getUriFromCnki(title,driver);
//        String uri = getUriFromGoole(title,driver);
//        String uri = getUriFromScienceDirect(title,driver);
        String uri = getUriFromIEEE(title,driver);
        log.info(uri);
        driver.close();
        return null;
    }

    public static String getUriFromCnki(String title,WebDriver driver){
        String url= "http://yuanjian.cnki.com.cn/";
        driver.get(url);
        WebElement textSearchKey =driver.findElement(By.id("textSearchKey"));
        textSearchKey.sendKeys(title);
        WebElement search =driver.findElement(By.id("search"));
        search.click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Html html = new Html(driver.getPageSource());
        List<String> ts = html.xpath("//*[@id=\"article_result\"]/div/div").all();
        for(String t: ts){
            Html ht = new Html(t);
            String title1 = ht.xpath("//*/div/p[1]/a/@title").get();
            String href = ht.xpath("//*/div/p[1]/a/@href").get();
            String authors = ht.xpath("//*/div/p[3]/span/@title").get();
            String qikan = ht.xpath("//*/div/p[3]/a/span/@title").get();
            if(null == qikan){
                qikan =  ht.xpath("//*/div/p[3]/span[3]/@title").get();
            }
            if(StringUtil.equals(title,title1)){
                return href;
            }
//            log.info(title1);
//            log.info(href);
//            log.info(authors);
//            log.info(qikan);
        }
        log.info(JSON.toJSONString(ts));
        return null;
    }

    public static void main(String[] args){
//            getRefUri("Influence of operating temperature on performance of electrostatic precipitator for pulverized coal combustion boiler",null,null);
            getRefUri("Recent development of electrostatic technologies in environmental remediation",null,null);
//            getRefUri("Ausmelt炉电除尘器设计",null,null);
//            getRefUri("Influence of operating temperature on performance of electrostatic precipitator for pulverized coal combustion boiler",null,null);

    }

    public static String getUriFromGoole(String title,WebDriver driver){
        String url= "https://www.google.com/search?q="+title+"&oq="+title+"&aqs=chrome..69i57&sourceid=chrome&ie=UTF-8";
        driver.get(url);
//        WebElement textSearchKey =driver.findElement(By.id("keyWords"));
//        textSearchKey.sendKeys(title);
//        WebElement search =driver.findElement(By.xpath("//*[@id=\"subform\"]/div[3]/div[3]/input"));
//        search.click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Html html = new Html(driver.getPageSource());
        List<String> ts = html.xpath("//*[@id=\"rso\"]/div/div/div/div/div/h3/a").all();
        for(String t: ts){
            Html ht = new Html(t);
            String href = ht.xpath("//*/a/@href").get();
            String title1 = ht.xpath("//*/a/text()").get().replace("...","").trim().toLowerCase();
            if(title.trim().toLowerCase().contains(title1)){
                driver.get(href);
                Html articleDetailHtml = new Html(driver.getPageSource());
                String articleTitle=null;
                if(StringUtil.contains(href,"sciencedirect.com")) {
                     articleTitle = articleDetailHtml.xpath("//*[@id=\"mathjax-container\"]/div[2]/article/h1/span/text()").get();
                }else if(StringUtil.contains(href,".ieee.org")){
                    articleTitle = articleDetailHtml.xpath("//*[@id=\"LayoutWrapper\"]/div/div/div/div[6]/div[3]/div/section[2]/div[2]/div[1]/div[1]/h1/span/text()").get();
                }else if(StringUtil.contains(href,".cnki.com.cn")){
                    articleTitle = articleDetailHtml.xpath("//*[@id=\"content\"]/div[2]/div[2]/h1/text()").get();
                }
                log.info(articleTitle);
                if(StringUtil.equals(title.trim().toLowerCase(),articleTitle.trim().toLowerCase())){
                    return href;
                }

            }
//            String authors = ht.xpath("//*/div/p[3]/span/@title").get();
//            String qikan = ht.xpath("//*/div/p[3]/a/span/@title").get();
//            if(null == qikan){
//                qikan =  ht.xpath("//*/div/p[3]/span[3]/@title").get();
//            }
//            if(StringUtil.equals(title,title1)){
//                return href;
//            }
        }
//        log.info(JSON.toJSONString(ts));
        return null;
    }

    public static String getUriFromScienceDirect(String title,WebDriver driver){
        String url= "https://www.sciencedirect.com/search?qs="+title+"&authors=&pub=&volume=&issue=&page=&origin=home&zone=qSearch";
        driver.get(url);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Html html = new Html(driver.getPageSource());
        List<String> ts = html.xpath("//*[@id=\"main_content\"]/main/div[1]/div[2]/div[2]/ol/li/div/div").all();
        for(String t: ts){
            t = StringUtil.replace(t,"</em>","");
            t = StringUtil.replace(t,"<em>","");
            Html ht = new Html(t.trim());
            String href = ht.xpath("//*/h2/a/@href").get();
            String title1 = ht.xpath("//*/h2/a/text()").get().trim().toLowerCase();
            if(StringUtil.equals(title1,title.trim().toLowerCase())){
                return "https://www.sciencedirect.com"+href;
            }
        }
        return null;
    }

    public static String getUriFromIEEE(String title,WebDriver driver){
        String url= "http://ieeexplore.ieee.org/search/searchresult.jsp?newsearch=true&queryText="+title;
        driver.get(url);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Html html = new Html(driver.getPageSource());
        List<String> ts = html.xpath("//*[@id=\"xplResultsContainer\"]/section[3]/div/div/div").all();
        for(String t: ts){
            t = StringUtil.replace(t,"<span class=\"highlight\">","");
            t = StringUtil.replace(t,"</span>","");
            Html ht = new Html(t.trim());
            String href = ht.xpath("//*/xpl-result/div[2]/div[2]/h2/a/@href").get();
            String title1 = ht.xpath("//*/xpl-result/div[2]/div[2]/h2/a/text()").get().trim().toLowerCase();
            if(StringUtil.equals(title1,title.trim().toLowerCase())){
                return "http://ieeexplore.ieee.org"+href;
            }
        }
        return null;
    }
}
