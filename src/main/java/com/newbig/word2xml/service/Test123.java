package com.newbig.word2xml.service;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import org.junit.Test;

import java.util.List;

/**
 * User: haibo
 * Date: 2018/1/25 下午8:41
 * Desc:
 */
public class Test123 {
    @Test
    public void tttt(){
        String[] testCase = new String[]{

                "周炳海，刘子龙",
                "何雪军[1]1，王进1，陆国栋1，刘振宇1，陈立2，金晶2",
                "HE Xue-jun1, WANG Jin1, LU Guo-dong1, LIU Zhen-yu1, CHEN Li2, JIN Jing"
        };
        Segment segment = HanLP.newSegment().enableNameRecognize(true);
        for (String sentence : testCase) {

            List<Term> termList = segment.seg(sentence);
            for(Term term: termList){
                if(term.nature.startsWith("nr")) {
                    System.out.println(term.word);
                }

            }
        }

        //
    }
}
