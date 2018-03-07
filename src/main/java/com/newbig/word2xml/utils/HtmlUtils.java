package com.newbig.word2xml.utils;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlUtils {
    //只删除部分html
    public static String delPartialHTMLTag(String htmlStr){
        List<String> patterns = Lists.newArrayList(
                "<script[^>]*?>[\\s\\S]*?<\\/script>",
                "<i [^>]*?>",
                "<!--[^>]*?>",
                "<div[^>]*?>",
                "<style[^>]*?>[\\s\\S]*?<\\/style>"
        );
        for(String pattern: patterns){
            htmlStr = deleteHtmlFlag(pattern,htmlStr);
        }

        htmlStr = StringUtils.replaceAll(htmlStr,"<p.*?>","<p>");
        htmlStr = StringUtils.replaceAll(htmlStr,"<spanlang.*?>","<spanlang>");
        htmlStr = StringUtils.replaceAll(htmlStr,"<span.*?>","");
        htmlStr = StringUtils.replaceAll(htmlStr,"<h2.*?>","<h2>");
        htmlStr = StringUtils.replaceAll(htmlStr,"<b.*?>","<b>");
        htmlStr = StringUtils.replaceAll(htmlStr,"<brclear.*?>","<brclear>");
        htmlStr = StringUtils.replaceAll(htmlStr,"<a.*?>","<a>");
        htmlStr = StringUtils.replaceAll(htmlStr,"<h1.*?>","<h1>");
        htmlStr = StringUtils.replaceAll(htmlStr,"<h2.*?>","<h2>");
        htmlStr = StringUtils.replaceAll(htmlStr,"<h3.*?>","<h3>");
        htmlStr = StringUtils.replaceAll(htmlStr,"<table .*?>","<table>");
        htmlStr = StringUtils.replaceAll(htmlStr,"<td .*?>","<td>");
        htmlStr = StringUtils.replaceAll(htmlStr,"<tr .*?>","<tr>");
        htmlStr = StringUtils.replace(htmlStr,"</span>","");
        htmlStr = StringUtils.replace(htmlStr,"<![if !supportLists]>","");
        htmlStr = StringUtils.replace(htmlStr,"<o:p>","");
        htmlStr = StringUtils.replace(htmlStr,"</o:p>","");
        htmlStr = StringUtils.replace(htmlStr,"<b>","");
        htmlStr = StringUtils.replace(htmlStr,"</b>","");
        htmlStr = StringUtils.replace(htmlStr,"<p>","");
        htmlStr = StringUtils.replace(htmlStr,"</p>","");
        htmlStr = StringUtils.replace(htmlStr,"<a>","");
        htmlStr = StringUtils.replace(htmlStr,"</a>","");
        htmlStr = StringUtils.replace(htmlStr,"<body>","");
        htmlStr = StringUtils.replace(htmlStr,"</body>","");
        htmlStr = StringUtils.replace(htmlStr,"<div>","");
        htmlStr = StringUtils.replace(htmlStr,"</i>","");
        htmlStr = StringUtils.replace(htmlStr,"</div>","");
        htmlStr = StringUtils.replace(htmlStr,"<![endif]>","");
        htmlStr = StringUtils.replace(htmlStr,"<head>","");
        htmlStr = StringUtils.replace(htmlStr,"<html","");
        htmlStr=htmlStr.replaceAll("&nbsp;"," "); //过滤html标签
        return htmlStr.trim(); //返回文本字符串
    }

    public static String deleteAllHtmlTag(String htmlStr){
        if(StringUtils.isEmpty(htmlStr)){
            return "";
        }
        List<String> patterns = Lists.newArrayList(
                "<script[^>]*?>[\\s\\S]*?<\\/script>",
                "<[^>]+>",
                "<style[^>]*?>[\\s\\S]*?<\\/style>"
        );
        for(String pattern: patterns){
            htmlStr = deleteHtmlFlag(pattern,htmlStr);
        }
        return htmlStr.trim(); //返回文本字符串
    }
    public static String deleteHtmlFlag(String htmlStyle,String html){
        Pattern pStyle=Pattern.compile(htmlStyle,Pattern.CASE_INSENSITIVE);
        Matcher mStyle=pStyle.matcher(html);
        html=mStyle.replaceAll("").trim(); //过滤style标签
        return html;
    }
    public static String getHtmlEncode(String filePath) {
        String charset = "GBK";
        byte[] first3Bytes = new byte[3];
        try {
            try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePath))) {
                bis.mark(0);
                int read = bis.read(first3Bytes, 0, 3);
                System.out.println("字节大小：" + read);
                if (read == -1) {
                    return charset; //文件编码为 ANSI
                } else if (first3Bytes[0] == (byte) 0xFF
                        && first3Bytes[1] == (byte) 0xFE) {
                    charset = "UTF-16LE"; //文件编码为 Unicode
                } else if (first3Bytes[0] == (byte) 0xFE
                        && first3Bytes[1] == (byte) 0xFF) {
                    charset = "UTF-16BE"; //文件编码为 Unicode big endian
                } else if (first3Bytes[0] == (byte) 0xEF
                        && first3Bytes[1] == (byte) 0xBB
                        && first3Bytes[2] == (byte) 0xBF) {
                    charset = "UTF-8"; //文件编码为 UTF-8
                }
                bis.reset();
                bis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return charset;
    }
}
