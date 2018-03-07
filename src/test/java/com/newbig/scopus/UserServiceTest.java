package com.newbig.scopus;


import com.newbig.scopus.model.Scopus;
import com.newbig.scopus.service.ResearchGateService;
import com.newbig.scopus.service.ScopusCrawlService;
import com.newbig.scopus.service.ScopusHomeService;
import com.newbig.scopus.service.ScopusService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT, classes = AppApplication.class)
public class UserServiceTest {
    @Autowired
    private ScopusCrawlService scopusCrawlService;
    @Autowired
    private ScopusHomeService scopusHomeService;
    @Autowired
    private ScopusService scopusService;
    @Autowired
    private ResearchGateService researchGateService;

    @Test
    public void ttt0() {
        List<Scopus> scopuses = scopusService.getScopusByStatus(3);
        System.out.println(scopuses.size());
    }

    @Test
    public void ttt1() {
        try {
            scopusHomeService.schedule();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void ttt2() {
        try {
            researchGateService.schedule();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void ttt3() {
            scopusService.updateScores();
    }

    @Test
    public void insert() {
        scopusService.insertScopus();
    }
}


