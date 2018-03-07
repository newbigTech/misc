package com.newbig.scopus;

import com.newbig.scopus.utils.SimilarityUtils;
import org.junit.Test;

public class SimilarityUtilsTest {

    @Test
    public void test(){
        System.out.println(SimilarityUtils.calculateSimilarityLCS("Cani, Patrice D.","Patrice D Cani"));
    }
}
