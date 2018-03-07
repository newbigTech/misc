package com.newbig.scopus.controller;


import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.newbig.scopus.dto.UserDTO;
import com.newbig.scopus.model.Author;
import com.newbig.scopus.model.Scopus;
import com.newbig.scopus.model.UserInfo;
import com.newbig.scopus.service.ScopusService;
import com.newbig.scopus.utils.FileTxtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LoggerConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;


@RestController
@Api(value = "App管理", description = "App管理")
@Slf4j
public class AppController {


    @Autowired
    private ScopusService scopusService;
    @GetMapping(value = "/getList")
    @ApiOperation(value = "getList", notes = "", httpMethod = "GET")
    public PageInfo<Scopus> getList(@RequestParam(required = false ,defaultValue = "1") Integer pageNum,
                                    @RequestParam(required = false ,defaultValue = "10") Integer pageSize) {

        return scopusService.getScopusList(pageNum,pageSize);
    }

    @PostMapping(value = "/doLogin")
    @ApiOperation(value = "登录", notes = "", httpMethod = "POST")
    public Map<String,Object> doLogin(@RequestBody UserDTO userDTO) {
        Map<String,Object> mso = Maps.newHashMap();
        Map<String,Object> mso1 = Maps.newHashMap();
        mso.put("code",20000);
        mso1.put("token","admin");
        mso.put("data",mso1);
        return mso;
    }
    @GetMapping(value = "/getUserInfo")
    @ApiOperation(value = "登录", notes = "", httpMethod = "GET")
    public Map<String,Object> getUserInfo() {
        Map<String,Object> mso = Maps.newHashMap();
        mso.put("code",20000);
        UserInfo ui = new UserInfo();
        ui.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        ui.setName("admin");
        ui.setRole(Lists.newArrayList("admin"));
        mso.put("data",ui);
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        context.getLogger("com.newbig.scopus.controller").setLevel(Level.ERROR);
        log.info("===--ee-----");
        context.getLogger("com.newbig.scopus.controller").setLevel(Level.INFO);
        log.info("===-12------");
        return mso;
    }
}
