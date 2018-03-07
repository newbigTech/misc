package com.newbig.word2xml.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.newbig.word2xml.utils.Constants.XREF_BIBR;
import static com.newbig.word2xml.utils.Constants.XREF_FIG;

/**
 * User: haibo
 * Date: 2018/1/26 上午10:22
 * Desc:
 */
public class StringUtil extends StringUtils {
    /**
     * 过滤字母
     * @param alphabet
     * @return
     */
    public static String filterAlphabet(String alphabet){
        return replaceAll(alphabet,"[A-Za-z]", "");
    }

    /**
     * 过滤数字
     * @param digital
     * @return
     */
    public static String filterDigital(String digital){
        return replaceAll(digital,"[0-9]", "");
    }

    /**
     * 过滤汉字
     * @param chin
     * @return
     */
    public static String filterChinese(String chin){
        return replaceAll(chin,"[\\u4e00-\\u9fa5]", "");
    }

    /**
     * 过滤 字母、数字、汉字
     * @param character
     * @return
     */
    public static String filterAll(String character){
        return replaceAll(character,"[a-zA-Z0-9\\u4e00-\\u9fa5]", "");
    }

    /**
     * 只保留中文
     * @param str
     * @return
     */
    public static String getChinese(String str){
        str =  replaceAll(str,"[^\u4E00-\u9FA5]", "");
        return str;
    }
    /**
     * 只保留英文字母
     * @param str
     * @return
     */
    public static String getAlphabet(String str){
        str =  replaceAll(str,"[^A-Za-z]", "");
        return str;
    }

    public static boolean isChineseOrEnglishOrDigit(String str) {
        return replaceAll(str,"[^a-zA-Z0-9\\u4e00-\\u9fa5]", "").length()>0;
    }
    public static boolean isChineseOrEnglish(String str) {
        return replaceAll(str,"[^a-zA-Z\\u4e00-\\u9fa5]", "").length()>0;
    }
    public static boolean isEnglishOGreek(String str) {
        return replaceAll(str,"[^a-zA-Zα-ω]", "").length()>0;
    }
    public static String getFisrtNumFromString(String str){
        if(StringUtil.isEmpty(str)){
            return "";
        }else if(StringUtil.isNumeric(str)){
            return str;
        }else{
            //去除空格
            str =  StringUtil.replace(str," ","");
            StringBuilder sb = new StringBuilder();
            for(int i=0;i<str.length();i++) {
                if (StringUtil.isNumeric(str.charAt(i)+"")
                        ||StringUtil.equals(".",str.charAt(i)+"")) {
                    sb.append(str.charAt(i)+"");
                }
                if(StringUtil.isChineseOrEnglish(str.charAt(i+1)+"")
                        ||StringUtil.equals(str.charAt(i+1)+"","(")
                        ||StringUtil.equals(str.charAt(i+1)+"","（")){
                    break;
                }

            }
            return sb.toString();
        }
    }
    public static String getFigureLinkId(String str){
        if(StringUtil.isEmpty(str)){
            return "";
        }else{
            //去除空格
            Pattern pattern = Pattern.compile("图(\\d+).*");
            Matcher matcher = pattern.matcher(str);
            if(matcher.matches()){
                return matcher.group(1);
            }else{
                return "";
            }
        }
    }
    public static String getImageSrc(String str){
        if(StringUtil.isEmpty(str)){
            return "";
        }else{
            //去除空格
            Pattern pattern = Pattern.compile(".*<img width=\\d+ height=\\d+ src=\"(.*)\" v.*");
            Matcher matcher = pattern.matcher(str);
            if(matcher.matches()){
                return matcher.group(1);
            }else{
                return "";
            }
        }
    }

    /**
     * 将英文字母 用 <italic></italic>包起来
     * @param tmp0
     * @return
     */
    public static String addItalic(String tmp0) {
        if(tmp0.length()<7){
            return tmp0;
        }
        if(tmp0.startsWith("<table>")){
            return tmp0;
        }
        StringBuilder sb = new StringBuilder();
        char[] chars = tmp0.toCharArray();
        for(int i=0;i<chars.length;i++){
            if(i<chars.length-5&&StringUtil.equals(tmp0.substring(i,i+5),"<sub>")){
                sb.append("<sub>");
                i=i+4;
            }else if(i<chars.length-6&&StringUtil.equals(tmp0.substring(i,i+6),"</sub>")){
                sb.append("</sub>");
                i=i+5;
            }else if(i<chars.length-5&&StringUtil.equals(tmp0.substring(i,i+5),"<sup>")){
                sb.append("<sup>");
                i=i+4;
            }else if(i<chars.length-6&&StringUtil.equals(tmp0.substring(i,i+6),"</sup>")){
                sb.append("</sup>");
                i=i+5;
            }else if(StringUtil.isEnglishOGreek(chars[i]+"")){
                sb.append("<italic>");
                while (StringUtil.isEnglishOGreek(chars[i]+"")){
                    sb.append(chars[i]);
                    i++;
                }
                sb.append("</italic>");
                i--;
            }else{
                sb.append(chars[i]);
            }
        }
        String tmp =  sb.toString();
        Pattern pattern = Pattern.compile(".*\\[(.*)\\].*");
        Matcher matcher = pattern.matcher(tmp);
        if(matcher.matches()){
            for(int i=0;;i++){
                try {
                    String t = matcher.group(i);
                    if (StringUtil.isEmpty(t)) {
                        break;
                    }
                    String t1 = StringUtil.replace(t, "<italic>", "");
                    t1 = StringUtil.replace(t1, "</italic>", "");
                    tmp = StringUtil.replace(tmp, t, t1);
                }catch (Exception e){
                    break;
                }
            }
        }
        return tmp;
    }

    public static String addRef(String tmp) {
        Pattern pattern = Pattern.compile(".*\\[(.*)\\].*");
        Matcher matcher = pattern.matcher(tmp);
        if(matcher.matches()){
            for(int i=1;;i++){
                try {
                    String t = matcher.group(i);
                    if (StringUtil.isEmpty(t)) {
                        break;
                    }
                    String t1 = t;
                    t=StringUtil.replace(t,"</sup>","");
                    t=StringUtil.replace(t,"<sup>","");
//                    System.out.println(">>>> "+t);
                    String[] refNums = t.split(",|-|，");
                    if(refNums.length == 1 && StringUtil.isNumeric(refNums[0])){
                        t = StringUtil.replace(t,refNums[0],XREF_BIBR.replace("${id}",refNums[0]));
                    }else{
                        for(String refNum: refNums){
                            if(StringUtil.isNumeric(refNum)){
                                //防止替换出错
                                t = StringUtil.replace(t,","+refNum,","+XREF_BIBR.replace("${id}",refNum));
                                t = StringUtil.replace(t,"-"+refNum,"-"+XREF_BIBR.replace("${id}",refNum));
                                t = StringUtil.replace(t,refNum+",",XREF_BIBR.replace("${id}",refNum)+",");
                                t = StringUtil.replace(t,refNum+"-",XREF_BIBR.replace("${id}",refNum)+"-");
                            }else{
                                throw new Exception("参考文献标注错误");
                            }
                        }
                    }
                    tmp = StringUtil.replace(tmp,t1,t);
                }catch (Exception e){
                    break;
                }
            }
        }
        return tmp;
    }

    public static String addFigureRef(String tmp) {
        Pattern pattern = Pattern.compile(".*(图[\\d+| \\d+]).*");
        Matcher matcher = pattern.matcher(tmp);
        if(matcher.matches()) {
            for (int i = 1; ; i++) {
                try {
                    String t = matcher.group(i);
                    if (StringUtil.isEmpty(t)) {
                        break;
                    }
                    System.out.println(">>>> " + t);
                    String figureNum = t.replace("图","").trim();
                    if(StringUtil.isNumeric(figureNum)){
                        String t1 = StringUtil.replace(t,t,XREF_FIG.replace("${name}",figureNum)
                                .replace("${id}",figureNum));
                        tmp = StringUtil.replace(tmp,t,t1);
                    }
                } catch (Exception e) {
                    break;
                }
            }
        }
        return tmp;
    }
}
