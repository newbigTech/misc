package com.newbig.scopus.utils;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.newbig.scopus.model.Author;
import com.newbig.scopus.model.Result;
import com.newbig.scopus.model.Scopus;
import com.newbig.scopus.model.Scopus2016;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FileTxtUtil {
    /**
     * 读取txt文件
     * @param path
     * @return
     */
    public static List<Author> readtxt(String path){
        List<Author> authors = Lists.newArrayList();
        File file = new File(path);
        try {
            InputStreamReader reader = new InputStreamReader(new FileInputStream(file),"gbk");
            BufferedReader br = new BufferedReader(reader);
            String s = null;
            while((s=br.readLine())!=null){
//                System.out.println(s);
                String[] ss = s.split("\t");
//                if(ss[4].contains("(")){
//                    ss[4] = ss[4].split("\\(")[0];
//                }
                Author author = new Author(ss[0],ss[1],ss[2],ss[3],ss[4],ss[5]);
                authors.add(author);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return authors;
    }
    public static List<Result> readtxt1(String path){
        List<Result> results = Lists.newArrayList();
        File file = new File(path);
        try {
            InputStreamReader reader = new InputStreamReader(new FileInputStream(file),"utf-8");
            BufferedReader br = new BufferedReader(reader);
            String s = null;
            while((s=br.readLine())!=null){
                Result result = JSON.parseObject(s,Result.class);
                results.add(result);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return results;
    }

    public static List<Scopus> readtxt1617(String path){
        List<Scopus> results = Lists.newArrayList();
        File file = new File(path);
        try {
            InputStreamReader reader = new InputStreamReader(new FileInputStream(file),"utf-8");
            BufferedReader br = new BufferedReader(reader);
            String s = null;
            int j1=0,j2=0;
            while((s=br.readLine())!=null){
                String[] ss = s.split(",",-1);
                System.out.println(ss[6].trim());
                if(Objects.equals(ss[1].trim(),"0" ) && Objects.equals(ss[2].trim(),"0" )){
                    j1++;
                }
                j2++;
//                Scopus scopus2016 = new Scopus();
//                scopus2016.setFirstName(ss[2]);
//                scopus2016.setLastName(ss[1]);
//                scopus2016.setPrimaryAffiliation(ss[3]);
//                scopus2016.setStatus(0);
//                results.add(scopus2016);
            }
            System.out.println("====="+j1 + "-"+ j2);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return results;
    }

    public static List<Scopus> readtxt122(String path){
        List<Scopus> results = Lists.newArrayList();
        File file = new File(path);
        Map<String,Integer> msi = Maps.newHashMap();
        try {
            BufferedReader br;
            String s;
            int j2;
            try (InputStreamReader reader = new InputStreamReader(new FileInputStream(file), "utf-8")) {
                br = new BufferedReader(reader);
                while((s=br.readLine())!=null) {
                    String[] ss = s.split("\t");
                    try {
                        if(Objects.equals(ss[2],"0")){
                            System.out.println(s);
                        }
                    }catch (Exception e){

                    }
                }
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return results;
    }
    public static void main(String[] args) {
         FileTxtUtil.readtxt122("/Users/haibo/Documents/newbigtech/ibe/db/dbo.pc_news.txt");
    }
}
