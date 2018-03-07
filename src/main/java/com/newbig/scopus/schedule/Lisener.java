package com.newbig.scopus.schedule;

import com.newbig.scopus.service.ScopusCrawlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Lisener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ScopusCrawlService scopusCrawlService;
//
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
//        //需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。
//        try {
//            scopusCrawlService.schedule();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
