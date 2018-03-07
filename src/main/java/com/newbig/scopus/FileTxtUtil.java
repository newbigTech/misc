package com.newbig.scopus;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

import java.io.*;
import java.util.List;

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
                if(ss[4].contains("(")){
                    ss[4] = ss[4].split("\\(")[0];
                }
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
    public static void main(String[] args) {
         FileTxtUtil.readtxt("C:\\Users\\haibo\\Desktop\\HCR query list.txt");
    }
}
