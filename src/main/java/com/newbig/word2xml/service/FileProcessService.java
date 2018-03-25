package com.newbig.word2xml.service;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Utf8;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.newbig.word2xml.model.*;
import com.newbig.word2xml.utils.HtmlUtils;
import com.newbig.word2xml.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.newbig.word2xml.utils.Constants.*;

@Slf4j
public class FileProcessService {
    private static Map<Integer, String> mis = Maps.newLinkedHashMap();
    private static Map<Integer, Map<Integer, String>> paraMap = Maps.newLinkedHashMap();
    private static ArticleMeta articleMeta = new ArticleMeta();
    private static JournalMeta journalMeta = new JournalMeta();
    private static String firstAuthor;//一作
    private static String comAuthor;//通讯作者

    private static Integer lineNum = 0;
    private static Integer keywordChNum;
    private static Integer keywordEnNum;
    private static Integer authorChNum;
    private static Integer authorEnNum;
    private static Integer referenceLineNum;
    private static Integer paramBeginLineNum;
    private static Integer introBeginLineNum;

    private static String authorChs = "";
    private static String authorEns = "";
    private static String afflicationCh = "";
    private static String afflicationEn = "";
    static {
        articleMeta.setPublisherId("zjdxxbgxb-51-5-841");
        articleMeta.setArtAccessId("1008-973X(2017)05-0841-06");
        articleMeta.setDoi("10.3785/j.issn.1008-973X.2017.05.001");
        articleMeta.setOther("U443.38");
        articleMeta.setSubject("=====");
        articleMeta.setSubjectEn("=====");
        DateType pubDate = new DateType("2018","10","10");
        articleMeta.setPubDate(pubDate);
        articleMeta.setReceivedDate(pubDate);
        articleMeta.setVolume("1");
        articleMeta.setIssue("1");
        articleMeta.setFpage("9");
        articleMeta.setLpage("10");
        Permissions permissions = new Permissions("版权所有&#169;《浙江大学学报(工学版)》编辑部2017",
                "Copyright &#169;2017 Journal of Zhejiang University (Engineering Science). All rights reserved.",
                "2018");
        articleMeta.setPermissions(permissions);
    }
    public static void main(String[] args) throws Exception {
//        process("/Users/haibo/Downloads/docx/G151113W.htm");
//        process("/Users/haibo/Downloads/docx/G160419W.htm");
//        process("/Users/haibo/Downloads/docx/1G151196.htm");
//        process("G:\\HHMIndesign\\docx\\G160092-2.html");
        process("/Users/haibo/Downloads/html/G160092-2.html");
        setAwardGroup();
        //下面的顺序不能变,后面的会依赖前面的行号
        setJournalMeta();
        getTitleAndAuthorCh();
        getAbstractAndKeywordsCh();
        getAbstractAndKeywordsEn();
        getTitleAndAuthorEn();
        getAfflications();//单位
        setContriGroupAndAfflication();
        Map<String,Object> dataMap = Maps.newHashMap();
        dataMap.put("journalMeta",journalMeta);
        dataMap.put("articleMeta",articleMeta);
        FrontObjectToXmlUtil.createXmlFile("src/main/resources","front.xml","UTF8",dataMap);
        getReferences();
        FrontObjectToXmlUtil.createXmlFile("src/main/resources","refs.xml","UTF8",dataMap);
        //

        getPara();
        getIntro();
    }

    private static void setAwardGroup() {
        for(int i=0;i<mis.size()-3;i++){
            if(HtmlUtils.deleteAllHtmlTag(mis.get(i)).startsWith("收稿日期")&&
                    HtmlUtils.deleteAllHtmlTag(mis.get(i+1)).startsWith("基金项目")&&
                    HtmlUtils.deleteAllHtmlTag(mis.get(i+2)).startsWith("作者简介")&&
                    HtmlUtils.deleteAllHtmlTag(mis.get(i+3)).startsWith("通信联系人")){
                DateType receivedDate = new DateType();
                String rdate = HtmlUtils.deleteAllHtmlTag(mis.get(i))
                                        .replace("收稿日期","")
                                        .replace(".","")
                                        .replace(":","")
                                        .replace("：","")
                                        .trim();
                if(StringUtil.isNotEmpty(rdate)){
                    String[] ss = rdate.split("-");
                    if(ss.length == 3){
                        receivedDate = new DateType(ss[0],ss[1],ss[2]);
                    }
                }
                articleMeta.setReceivedDate(receivedDate);
                String fundingsm = HtmlUtils.deleteAllHtmlTag(mis.get(i+1))
                        .replace("基金项目","")
                        .replace(":","")
                        .replace("：","")
                        .trim();
                FundingGroup fundingGroup = new FundingGroup();
                fundingGroup.setFundingStatement(fundingsm);
                String[] fs = fundingsm.split(";|；");
                List<AwardGroup> awardGroups = Lists.newArrayList();
                for(String f:fs){
                    Pattern pattern = Pattern.compile(".*(\\(.*)\\)");
                    Matcher matcher = pattern.matcher(f.trim());
                    if(matcher.matches()){
                        String s = matcher.group(1);
                        String fundingSource = f.replace(s+")","").trim();
                        String[] ss = s.replace("(","").split(",|，");
                        for(String str:ss){
                            AwardGroup awardGroup = new AwardGroup();
                            awardGroup.setCountry("CN");
                            awardGroup.setAwardId(str);
                            awardGroup.setFundingSource(fundingSource);
                            awardGroups.add(awardGroup);
                        }
                    }
                }
                fundingGroup.setAwardGroups(awardGroups);
                articleMeta.setFundingGroup(fundingGroup);
                firstAuthor = HtmlUtils.deleteAllHtmlTag(mis.get(i+2))
                        .replace("作者简介","")
                        .replaceFirst(":","")
                        .replaceFirst("：","")
                        .trim();
                comAuthor = HtmlUtils.deleteAllHtmlTag(mis.get(i+3))
                        .replace("通信联系人","")
                        .replaceFirst(":","")
                        .replaceFirst("：","")
                        .trim();
                break;
            }
        }
    }

    private static void setContriGroupAndAfflication() throws Exception {
        List<String> chs = Lists.newArrayList(
                authorChs.replaceFirst(":", "")
                        .replaceFirst("：", "")
                        .trim()
                        .split(",|，")
        );
        List<String> ens = Lists.newArrayList(
                authorEns.replaceFirst(":", "")
                        .replaceFirst("：", "")
                        .trim()
                        .split(",|，")
        );
        //单位
//        if ((afflicationCh.contains(";") || afflicationCh.contains("；"))
//                && (afflicationEn.contains(";") || afflicationEn.contains("；"))) {
            List<String> affCh = Lists.newArrayList(
                    afflicationCh.replaceFirst("\\(", "")
                            .replaceFirst("\\)", "")
                            .replaceFirst("（", "")
                            .replaceFirst("）", "")
                            .trim()
                            .split(";|；")
            );
            List<String> affEn = Lists.newArrayList(
                    afflicationEn.replaceFirst("\\(", "")
                            .replaceFirst("\\)", "")
                            .replaceFirst("（", "")
                            .replaceFirst("）", "")
                            .trim()
                            .split(";|；")
            );
            List<Aff> affs = Lists.newArrayList();
            for (int i = 0; i < affCh.size(); i++) {
                Aff aff = new Aff();
                aff.setLabel((i + 1) + "");
                aff.setId("aff" + (i + 1));
                aff.setAddrLine(affCh.get(i));
                aff.setAddrLineEn(affEn.get(i));
                affs.add(aff);
            }
        String authorName = firstAuthor.split("\\(|（")[0].trim();
        String comName = comAuthor.split("，|,")[0].trim();
        List<Contrib> contribs = Lists.newArrayList();
            if (chs.size() == ens.size()) {
                for (int i = 0; i < chs.size(); i++) {
                    Contrib contrib = new Contrib();
                    contrib.setGivenName(chs.get(i).trim().substring(0, 1).trim());
                    contrib.setSurname(chs.get(i).trim().substring(1, chs.get(i).length()).trim());
                    contrib.setGivenNameEn(ens.get(i).trim().split(" ")[0].trim());
                    contrib.setSurnameEn(ens.get(i).trim().replaceFirst(contrib.getGivenNameEn(),"").trim());
                    if(StringUtil.equals(chs.get(i).trim(),authorName)){
                        contrib.setBio(firstAuthor);
                        contrib.setEmail("");
                    }
                    if(StringUtil.equals(chs.get(i).trim(),comName)){
                        contrib.setEmail("");
                    }
                    if (affs.size() == 1) {
                        contrib.setAffIds(Lists.newArrayList("1"));
                    } else {
                        //TODO 单位有多个的情况
                    }
                    contribs.add(contrib);
                }
            } else {
                throw new Exception("中文作者个数 与英文作者个数不相同");
            }
            articleMeta.setContribGroup(contribs);
            articleMeta.setAffs(affs);
//        }
    }

    private static void setJournalMeta() {
        journalMeta.setIssn("1008-973X");
        journalMeta.setJournalId("ZJDXXBGXB");
        journalMeta.setJournalTitle("浙江大学学报(工学版)");
        journalMeta.setJournalTitleEn("Journal of Zhejiang University (Engineering Science)");
        journalMeta.setPublisherLoc("杭州市天目山路148号浙江大学西溪校区出版社406室");
        journalMeta.setPublisherName("浙江大学学报(工学版)编辑部");
    }

    private static void getIntro() {
        print(".........Intro begin.......");
        for (Integer i = keywordEnNum + 1; i < paramBeginLineNum; i++) {
            String intro = HtmlUtils.deleteAllHtmlTag(mis.get(i));
            if (StringUtils.isNotEmpty(intro)
                    && !StringUtils.startsWithAny(intro, "收稿日期", "基金项目", "作者简介", "通信联系人")) {
                if (null == introBeginLineNum) {
                    introBeginLineNum = i;
                }
                print(mis.get(i));
            }
        }
        print(".........Intro end.........");
    }

    public static void process(String path) {
        String charset = getHtmlCharset(path);
        File file = new File(path);
        try {
            BufferedReader br;
            String s;
            String s1 = "";
            try (InputStreamReader reader = new InputStreamReader(new FileInputStream(file), charset)) {
                br = new BufferedReader(reader);
                while ((s = br.readLine()) != null) {
                    if (s.trim().length() > 0) {
                        s1 = s1 + " " + s;
                    } else {
                        String p = HtmlUtils.delPartialHTMLTag(s1);
                        if (p.length() > 0) {
//                            System.out.println(p);
//                            System.out.println();
                            mis.put(lineNum, p);
                            lineNum++;
                        }
                        s1 = "";
                    }
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private static void getPara(){
        List<Integer> pLineNumList = Lists.newArrayList();
        for(Integer i=keywordEnNum+1;i< lineNum;i++){
            String p = mis.get(i);
            //TODO .待优化
            if(p.length()>0 && p.startsWith("<h")&&!p.startsWith("<hr")&&(!HtmlUtils.deleteAllHtmlTag(p).split(" ")[0].contains("."))
                    &&!p.contains("参考文献")){
                print(i+"==>"+p);
                if(null == paramBeginLineNum) {
                    paramBeginLineNum = i;
                }
                pLineNumList.add(i);
            }
        }
        for(Integer i=0;i<pLineNumList.size();i++){
            String title=HtmlUtils.deleteAllHtmlTag(mis.get(pLineNumList.get(i)));
            print((i+1)+"开始:"+ title);
            String id = StringUtil.getFisrtNumFromString(title);
            title= StringUtil.replace(title,id,"").trim();
            print(SEC_BEGIN.replace("${id}",id.replace(".","-"))
                    .replace("${label}",id)
                    .replace("${title}",title)
            );
            Map<Integer,String> pmss = Maps.newLinkedHashMap();
            for(Integer j=pLineNumList.get(i)+1;((i+1<pLineNumList.size()&&j<pLineNumList.get(i+1)) || (i+1==pLineNumList.size()&&j< referenceLineNum));j++){
//                print("++++"+mis.get(j));

                String tmp0 = HtmlUtils.deleteAllHtmlTag(mis.get(j));
                String tmp1 = HtmlUtils.deleteAllHtmlTag(mis.get(j+1));

                String figureRefId = StringUtil.getFigureLinkId(tmp0);
//                if(figureRefId!=null&&figureRefId.length()>0&&!tmp1.startsWith("Fig")){
////                        tmp0 = HtmlUtils.delPartialHTMLTag(mis.get(j)).replace(" ", "")
////                                .replaceAll("图" + figureRefId,
////                                        XREF_FIG.replace("${id}", StringUtil.getFisrtNumFromString(figureRefId))
////                                                .replace("${name}", figureRefId));
//                        print(mis.get(j));
//                }else
                if(tmp0.startsWith("图")&&tmp1.startsWith("Fig")){
                    String figId = StringUtil.getFisrtNumFromString(tmp0.replace("图",""));
                    Figure figure = new Figure();
                    figure.setId(figId);
                    figure.setFigureNameCh(tmp0.replaceFirst("图","").trim()
                            .replaceFirst(figId,"").trim());
                    figure.setFigureNameEn(tmp1.replaceFirst("Fig.","").trim()
                            .replaceFirst(figId,"").trim());
                    String imgSrc = StringUtil.getImageSrc(mis.get(j-1));
                    figure.setFigureLink(imgSrc);
                    pmss.put(j+1,mis.get(j+1));
                    j=j+1;
                    print(figure.toString());
                }else if(tmp0.startsWith("表")&&tmp1.startsWith("Tab")){
                    print("表::::"+tmp0);
                    print("表::::"+tmp1);
                    pmss.put(j+1,mis.get(j+1));
                    j=j+1;
                }else if(tmp0.startsWith("\\[")){
                    String formulaId = StringUtil.getFisrtNumFromString(StringUtil.reverse(tmp0));
                    String tmp = StringUtil.reverse(tmp0);
                    String formula = tmp.replaceFirst("\\)","")
                            .replaceFirst("\\(","")
                            .replaceFirst("\\（","")
                            .replaceFirst("\\）","")
                            .replaceFirst(formulaId,"").trim();
                    formula = StringUtil.reverse(formula)
                            .replace("\\].","")
                            .replace("\\[","");

                    Formula f = new Formula(formulaId,formula);
                    print(f.toString());
                }else{
                    if(StringUtil.isNotEmpty(tmp0)) {
                        tmp0 = mis.get(j);
                        tmp0 = StringUtil.addItalic(tmp0);
                        if(tmp0.contains("\\[")&&tmp0.contains("\\]")){
                            tmp0 = StringUtil.replace(tmp0,"\\[","$");
                            tmp0 = StringUtil.replace(tmp0,"\\]","$");
                        }
                        tmp0 = StringUtil.addRef(tmp0);
                        tmp0 = StringUtil.addFigureRef(tmp0);
                        print("<p>" + tmp0 + "</p>");
                    }
                }
            }
            paraMap.put(i,pmss);
            print(SEC_END);
            print((i+1)+"结束");
        }
        print("paragraph length "+ paraMap.size());
    }

    public static void getTitleAndAuthorCh() {
        for (Integer i = 0; i < lineNum - 2; i++) {
            String p0 = HtmlUtils.deleteAllHtmlTag(mis.get(i));
            String p1 = HtmlUtils.deleteAllHtmlTag(mis.get(i + 1));
            String p2 = HtmlUtils.deleteAllHtmlTag(mis.get(i + 2));
            //正常情况下 P2对应单位
            if (p2.startsWith("(") || p2.startsWith("（")) {
                //标题只有一行
                if (StringUtil.isChineseOrEnglishOrDigit(p0.substring(0, 1))) {
                    articleMeta.setArticleTitle(p0);
                    authorChs = p1;
                    print("标题:" + p0);
                    print("作者:" + p1);
                    authorChNum = i + 2;
                    break;
                } else {
                    //标题有两行的情况
                    p0 = HtmlUtils.deleteAllHtmlTag(mis.get(i - 1)) + ":" + p0;
                    articleMeta.setArticleTitle(p0);
                    authorChs = p1;
                    print("标题:" + p0);
                    print("作者:" + p1);
                    authorChNum = i + 2;
                    break;
                }
            }
        }
    }

    public static void getTitleAndAuthorEn() {
        if (HtmlUtils.deleteAllHtmlTag(mis.get(keywordChNum + 1)).startsWith("中图分类")) {
            String t = HtmlUtils.deleteAllHtmlTag(mis.get(keywordChNum + 3));
            if (StringUtil.isChineseOrEnglishOrDigit(t.substring(0, 1))) {
                print("英文标题:" + mis.get(keywordChNum + 2));
                print("英文作者:" + mis.get(keywordChNum + 3));
                articleMeta.setTransTitle( mis.get(keywordChNum + 2));
                authorEns =  mis.get(keywordChNum + 3);
                authorEnNum = keywordChNum + 3;
            } else {
                print("英文标题:" + mis.get(keywordChNum + 2) + "::" + mis.get(keywordChNum + 3));
                print("英文作者:" + mis.get(keywordChNum + 4));
                articleMeta.setTransTitle( mis.get(keywordChNum + 2));
                authorEns =  mis.get(keywordChNum + 4);
                authorEnNum = keywordChNum + 4;
            }
        } else {
            String t = HtmlUtils.deleteAllHtmlTag(mis.get(keywordChNum + 2));
            if (StringUtil.isChineseOrEnglishOrDigit(t.substring(0, 1))) {
                print("英文标题:" + mis.get(keywordChNum + 1));
                print("英文作者:" + mis.get(keywordChNum + 2));
                articleMeta.setTransTitle( mis.get(keywordChNum + 1));
                authorEns =  mis.get(keywordChNum + 2);
                authorEnNum = keywordChNum + 2;
            } else {
                print("英文标题:" + mis.get(keywordChNum + 1) + "::" + mis.get(keywordChNum + 2));
                print("英文作者:" + mis.get(keywordChNum + 3));
                articleMeta.setTransTitle( mis.get(keywordChNum + 1)+ " " + mis.get(keywordChNum + 2));
                authorEns =  mis.get(keywordChNum + 3);
                authorEnNum = keywordChNum + 3;
            }

        }

    }

    public static void getAbstractAndKeywordsCh() {
        for (Integer i = 1; i < lineNum - 2; i++) {
            String p0 = HtmlUtils.deleteAllHtmlTag(mis.get(i - 1));
            String p1 = HtmlUtils.deleteAllHtmlTag(mis.get(i));
            String p2 = HtmlUtils.deleteAllHtmlTag(mis.get(i + 1));
            if ((p0.endsWith(")") || p0.endsWith("）")) && p1.replace(" ", "").startsWith("摘要")
                    && p2.replace(" ", "").startsWith("关键词")) {
                print("摘要:" + p1);
                print("关键词:" + p2);
                articleMeta.setAbstractCh(p1);
                String[] kwdsCh = p2.replaceFirst("关键词", "")
                        .replaceFirst(":", "")
                        .replaceFirst("：", "")
                        .trim()
                        .split(";|；");
                articleMeta.setKwdGroupCh(Lists.newArrayList(kwdsCh));
                keywordChNum = i + 1;
                break;
            }
        }
    }

    public static void getAbstractAndKeywordsEn() {
        for (Integer i = 1; i < lineNum - 2; i++) {
            String p0 = HtmlUtils.deleteAllHtmlTag(mis.get(i - 1));
            String p1 = HtmlUtils.deleteAllHtmlTag(mis.get(i));
            String p2 = HtmlUtils.deleteAllHtmlTag(mis.get(i + 1));
            if ((p0.endsWith(")") || p0.endsWith("）")) && p1.startsWith("Abstract") && (p2.startsWith("Keyword") ||
                    p2.startsWith("Key words"))) {
                print("Abstract:" + p1);
                print("Keywords:" + p2);
                articleMeta.setAbstractEn(p1);
                String[] kwdsCh = p2.replaceFirst("Keywords", "")
                        .replaceFirst("Key words", "")
                        .replaceFirst(":", "")
                        .replaceFirst("：", "")
                        .trim()
                        .split(";|；");
                articleMeta.setKwdGroupEn(Lists.newArrayList(kwdsCh));
                keywordEnNum = i + 1;
                break;
            }
        }
    }

    public static void getAfflications() {
        StringBuilder sb = new StringBuilder();
        Integer temp = authorChNum;
        for (int i = 0; ; i++) {
            String a = HtmlUtils.deleteAllHtmlTag(mis.get(temp));
            temp++;
            sb.append(a);
            if (a.endsWith(")") || a.endsWith("）")) {
                break;
            }
        }
        print("单位" + sb.toString());
        afflicationCh = sb.toString();
        sb = new StringBuilder();
        temp = authorEnNum + 1;
        for (int i = 0; ; i++) {
            String a = HtmlUtils.deleteAllHtmlTag(mis.get(temp));
            temp++;
            sb.append(a);
            if (a.endsWith(")") || a.endsWith("）")) {
                break;
            }
        }
        afflicationEn = sb.toString();
        print("单位英文" + sb.toString());
    }

    public static void getReferences() throws Exception {
        List<String> refs = Lists.newArrayList();
        for (Integer i = 0; i < lineNum; i++) {
            String p = HtmlUtils.deleteAllHtmlTag(mis.get(i));
            String pNext = HtmlUtils.deleteAllHtmlTag(mis.get(i + 1));
            if (p.length() > 0 && p.startsWith("参考文献") &&
                    pNext.startsWith("[")) {
                print("参考文献开始 行号: " + i + " " + p);
                referenceLineNum = i;
                for (Integer j = i + 1; ; j++) {
                    if (null == mis.get(j)) {
                        break;
                    }
                    String pj = HtmlUtils.deleteAllHtmlTag(mis.get(j));

                    if (pj.length() == 0 ) {
                        break;
                    }
                    pNext = HtmlUtils.deleteAllHtmlTag(mis.get(j + 1));
                    if(!StringUtil.startsWithAny(pNext,"[")){
                        pj = pj+REF_SEPRATOR+pNext;
                    }
                    if(pj.contains(".     ")){
                        pj = pj.replace(".     ",REF_SEPRATOR);
                    }
                    if(pj.startsWith("[")){
                        refs.add(pj);
                        print(pj);
                    }
                }
                break;
            }
        }
        for(int i=0;i< refs.size();i++){
            if(!refs.get(i).contains("["+(i+1))){
//                throw new Exception("参考文献序号不连续，缺少["+(i+1)+"]");
            }
        }
        List<Reference> references = Lists.newArrayList();
        for(int i=0;i<refs.size();i++){
            String ref=refs.get(i);
            Reference reference = new Reference();
            reference.setLabel(i+1+"");
            //有中文和英文
            ref = StringUtil.replace(ref,"["+(i+1)+"]","").trim();
            ref = StringUtil.replace(ref,"["+(i+2)+"]","").trim();
            if(ref.contains(REF_J)) {
                if (ref.contains(REF_SEPRATOR)) {
                    String[] ss0 = ref.split(REF_SEPRATOR);
                    ElementCitation ch = buidCitation(ss0[0],true);
                    ElementCitation en = buidCitation(ss0[1],false);
                    reference.setCitationCh(ch);
                    reference.setCitationEn(en);
                }else{
                    //只有英文
                    ElementCitation en = buidCitation(ref,false);
                    reference.setCitationEn(en);
                }
            }else{
                //除了期刊 其他的不需要分割很细
                if (ref.contains(REF_SEPRATOR)) {
                    reference.setNote(ref.split(REF_SEPRATOR)[0]);
                    reference.setNoteEn(ref.split(REF_SEPRATOR)[1]);
                }else{
                    reference.setNoteEn(ref);
                }
            }
            references.add(reference);
        }
        articleMeta.setReferences(references);
        print("参考文献结束 ");
    }
    public static ElementCitation buidCitation(String ref,boolean flag){
        String[] ss1 = ref.trim().split("\\.| // ");
        List<String> refAuthors = Lists.newArrayList(ss1[0].split(",|，"));
        List<Name> ln = Lists.newArrayList();
        for (String refAuthor : refAuthors) {
            refAuthor = refAuthor.trim();
            if(StringUtil.equals(refAuthor,"et al")){
                continue;
            }
            if(StringUtil.equals(refAuthor,"等")){
                continue;
            }
            Name name = new Name();
            //TODO 姓名 后面要做统一处理
            if(flag) {
                name.setSurname(refAuthor.substring(0, 1));
                name.setGivenName(refAuthor.substring(1, refAuthor.length()));
                ln.add(name);
            }else{
                name.setSurname(refAuthor.split(" ")[0]);
                name.setGivenName(refAuthor.replace(name.getSurname(),"").trim());
                ln.add(name);
            }
        }
        ElementCitation citation = new ElementCitation();
        citation.setAnnotation(ref);
        citation.setPersonGroup(ln);
        String at = ref.trim().replace(ss1[0],"")
                                .replace(ss1[ss1.length-1],"").trim();
        at = StringUtil.replaceFirst(at,".","").trim();
        at = StringUtil.reverse(at).trim();
        at = StringUtil.replaceFirst(at,".","").trim();
        at = StringUtil.reverse(at).trim();
        citation.setArticleTitle(at.replace(REF_J, "").trim());
        String[] ss2 = ss1[ss1.length-1].split(",|，");
        citation.setSource(ss2[0].trim());
        citation.setYear(ss2[1].trim());
        String[] ss3 = ss2[2].split("\\(|:|：|-");
        if(ss2[2].contains("(")){
            citation.setVolume(ss3[0].trim());
            citation.setIssue(ss3[1].replace(")", "")
                    .replace("）", "").trim());
            citation.setFpage(ss3[2].trim());
            citation.setLpage(ss3[3].trim());
        }else{
            citation.setVolume(ss3[0].trim());
            citation.setFpage(ss3[1].trim());
            citation.setLpage(ss3[2].trim());
            citation.setIssue("");
        }
        //TODO
        citation.setUri("uri");
        return citation;
    }
    public static void print(String msg) {
        System.out.println(msg);
    }

    public static String getHtmlCharset(String filePath) {
//        File file = new File(filePath);
//        try {
//            BufferedReader br;
//            String s;
//            try (InputStreamReader reader = new InputStreamReader(new FileInputStream(file), "gb18030")) {
//                br = new BufferedReader(reader);
//                while((s=br.readLine())!=null) {
//                    s = s.trim();
//                    if(s.length()>0 && s.startsWith("<meta") && s.contains("charset")) {
//                        Pattern pattern = Pattern.compile("<meta.* charset=(.*)\">");
//                        Matcher matcher = pattern.matcher(s);
//                        if(matcher.matches() ){
//                            return matcher.group(0);
//                        }
//                    }
//                }
//            }
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        return "gb2312";
    }
}
