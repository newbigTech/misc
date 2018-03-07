package com.newbig.scopus.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.newbig.scopus.model.Scopus;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class HtmlExtractor {
    public static void readtxt122(String path){
        File file = new File(path);
        try {
            BufferedReader br;
            String s;
            try (InputStreamReader reader = new InputStreamReader(new FileInputStream(file), "GB2312")) {
                br = new BufferedReader(reader);
                while((s=br.readLine())!=null) {
                    System.out.println(s);
                }
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    public static void main(String[] args) {
        readtxt122("/Users/haibo/Downloads/docx/G151113W.htm");
    }
}
