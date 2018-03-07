package com.newbig.scopus;

import com.newbig.scopus.config.AppConfig;
import com.newbig.scopus.service.ScopusCrawlService;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "com.newbig.scopus.mapper")
@Slf4j
public class AppApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(AppConfig.class);
        app.run(AppApplication.class, args);
        log.info("--------------------------------");
    }
}
