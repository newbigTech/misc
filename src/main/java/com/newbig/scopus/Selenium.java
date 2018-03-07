package com.newbig.scopus;


import com.alibaba.fastjson.JSON;
import com.google.common.base.Splitter;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Selenium {

    public static void main(String[] args) throws Exception {

        List<Result> lr =   FileTxtUtil.readtxt1("G:\\Work\\scopus\\log\\bizLog\\success.log");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("docsPerPage", 20);
        map.put("offset", "0");
        map.put("authorProfileOrigin",true);
        map.put("sort", "pubyearDescend");
        map.put("startYear", 2013);
        map.put("endYear", 2017);
        map.put("excludeAuthors", "on");
        map.put("excludeArticleAuthors", "off");
        map.put("excludeBooks", "off");
        String url = lr.get(0).getUrl();
        System.out.println(url);
        Map<String,String> mss = Splitter.on("&")
                .withKeyValueSeparator("=")
                .split(url);
        url="https://www.scopus.com/cto2/getUpdate.uri?stateKey={stateKey}&amp;origin=cto&rebrandResultsPage=true"
        .replace("{stateKey}",mss.get("stateKey"));
        String body = send(url, map,"utf-8");
        System.out.println("交易响应结果：");
//        System.out.println(body);
        String rl = "https://www.scopus.com/cto2/status?ctoId={ctoid}&startYear=2013&endYear=2017&stateKey={stateKey}";
        rl = rl.replace("{ctoid}",mss.get("CTO_ID")).replace("{stateKey}",mss.get("stateKey"));
        System.out.println(rl);
    }

    public static String send(String url, Map<String,Object> map, String encoding) throws ParseException, IOException {
        String body = "";

        //创建httpclient对象
        CloseableHttpClient client = HttpClients.createDefault();
        //创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);

        //装填参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if(map!=null){
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
            }
        }
        //设置参数到请求对象中
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));

        System.out.println("请求地址："+url);
        System.out.println("请求参数："+nvps.toString());

        //设置header信息
        //指定报文头【Content-type】、【User-Agent】
        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
        httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        //执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = client.execute(httpPost);
        //获取结果实体
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            //按指定编码转换结果实体为String类型
            body = EntityUtils.toString(entity, encoding);
        }


        EntityUtils.consume(entity);
        //释放链接
        response.close();
        return body;
    }
}
