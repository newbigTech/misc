package com.newbig.scopus.utils;

public class SimilarityUtils {

        /**
         * 判断两段文本相似度
         * @param s1
         * @param s2
         * @return
         */
        public static Boolean calculateSimilarityLCS(String s1, String s2) {
            s1 = s1.trim().replace(",","");
            s2 = s2.trim().replace(",","");
            if(Math.abs(s1.length()-s2.length())>4){
                return false;
            }
//            String[] ss = s1.split(" ");

            char[] chars1 = s1.toCharArray();
            int i=0;
            for(char c: chars1){
                if(s2.contains(c+"")){
                    i++;
                }
            }
            return i*1.00f/s2.length()>0.9;
        }


}
