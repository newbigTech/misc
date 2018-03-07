package com.newbig.scopus;


import com.alibaba.fastjson.JSON;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;

import java.util.List;


public class Selenium1 {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Seting browser");
        System.setProperty("webdriver.chrome.driver","/Users/haibo/Documents/work/scopus/chromedriver");
//        System.setProperty("webdriver.gecko.driver","/Users/haibo/Documents/work/scopus/geckodriver");
//        System.setProperty("webdriver.ie.driver","C:\\Users\\haibo\\Desktop\\IEDriverServer_x64_3.5.1\\IEDriverServer.exe");
        WebDriver driver = null;

        List<Author> as = FileTxtUtil.readtxt("/Users/haibo/Documents/work/scopus/HCR query list - 副本.txt");
        driver= new ChromeDriver();
//        driver.manage().window().setSize(new Dimension(960, 800));
//        driver.get("https://www.scopus.com/freelookup/form/author.uri?zone=&origin=NO%20ORIGIN%20DEFINED");
//        Thread.sleep(6000);
//        Author author = as.get(3);
        driver.manage().getCookies().clear();
        driver.close();

        for(Author author: as) {
            try {
                driver.get("https://www.scopus.com/freelookup/form/author.uri?zone=&origin=NO%20ORIGIN%20DEFINED");
                WebElement lastName = driver.findElement(By.id("lastname"));
                lastName.clear();
                lastName.sendKeys(author.getLastName());
                WebElement firstName = driver.findElement(By.id("firstname"));
                firstName.clear();
                firstName.sendKeys(author.getAbbr());
                WebElement institute = driver.findElement(By.id("institute"));
                institute.clear();
                institute.sendKeys(author.getAffiliation());
                driver.manage().getCookies().clear();
                WebElement authorSubmitBtn = driver.findElement(By.id("authorSubmitBtn"));
                authorSubmitBtn.sendKeys(Keys.ENTER);
                Thread.sleep(1000);
                Page page = new Page();
                page.setHtml(new Html(driver.getPageSource()));
                List<String> authors = page.getHtml().xpath("//*[@class='authorResultsNamesCol col20']/a/text()").all();
                List<String> urls = page.getHtml().xpath("//*[@class='authorResultsNamesCol col20']/a/@href").all();
                driver.manage().getCookies().clear();
                driver.get(urls.get(0));
                page = new Page();
                page.setHtml(new Html(driver.getPageSource()));
                String name = page.getHtml().xpath("//*[@id=\"profileleftinside\"]/div[2]/div[1]/h1/text()").get();
                String school = page.getHtml().xpath("//*[@id=\"profileleftinside\"]/div[2]/div[1]/div[1]/text()").get();
                String authorId = page.getHtml().xpath("//*[@id=\"profileleftinside\"]/div[2]/div[1]/div[2]/text()").get();
                String hindex = page.getHtml().xpath("//*[@id=\"authLeftList\"]/li[3]/div[2]/span[1]/text()").get();
                Result result = new Result();
                result.setAuthor(author);
                result.setAuthorId(authorId.replace("作者 ID: ",""));
                result.sethIndex(hindex);
                result.setRealAffiliation(school);
                result.setRealName(name);
                driver.manage().getCookies().clear();
                Thread.sleep(2000);
                JavascriptExecutor j1 = (JavascriptExecutor) driver;
                j1.executeScript("document.getElementById('authViewCitOver').setAttribute('href','javascript:void(0);'); ");
                j1.executeScript("document.getElementById('authViewCitOver').click(); ");
                Thread.sleep(10000);
                driver.manage().getCookies().clear();
//                while(true){
//                    if(!driver.getCurrentUrl().contains("?authorId=")){
//                        break;
//                    }
//                    driver.get(driver.getCurrentUrl());
//                    Thread.sleep(4000);
//                }
//                result.setUrl(driver.getCurrentUrl());
//                LogUtil.info("success",JSON.toJSONString(result));
                JavascriptExecutor j = (JavascriptExecutor) driver;
                j.executeScript("document.getElementById('excludeAuthors').value='on'; ");
                Thread.sleep(300);
                j.executeScript("document.getElementById('updateOverviewButtonOn').click(); ");

                Thread.sleep(1000);
                Page page1 = new Page();
                page1.setHtml(new Html(driver.getPageSource()));
                String authorId1 = page1.getHtml().xpath("//*[@id=\"authorIdText\"]/text()").get();
                String hIndex = page1.getHtml().xpath("//*[@id=\"hirsch_index_open\"]/text()").get();
                System.out.println(authorId1 + "===----===" + hIndex);
                List<String> aaa = page1.getHtml().xpath("//*[@class='anchorText secondaryLink']/strong/text()").all();
                result.setUrl(JSON.toJSONString(aaa));
                LogUtil.info("success1",JSON.toJSON(result));
            } catch (Exception e) {
                e.printStackTrace();
                LogUtil.info("error", JSON.toJSONString(author));
            } finally {
            }
        }
        driver.quit();



    }
}
